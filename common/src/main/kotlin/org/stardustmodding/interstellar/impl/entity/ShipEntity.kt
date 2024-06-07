package org.stardustmodding.interstellar.impl.entity

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.EntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.registry.Registries
import net.minecraft.registry.SimpleDefaultedRegistry
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.stardustmodding.interstellar.api.data.Tracked
import org.stardustmodding.interstellar.api.entity.PhysicsEntity
import org.stardustmodding.interstellar.api.math.McExtensions.toPx
import org.stardustmodding.interstellar.api.physics.TransformExt.pos
import org.stardustmodding.interstellar.api.physics.Physics
import physx.geometry.PxBoxGeometry

@Suppress("RedundantNullableReturnType")
class ShipEntity(type: EntityType<*>, world: World) : PhysicsEntity(type, world) {
    // The blocks map *has* to be nullable (so we can do the checks without IDEA yelling at us),
    // or Minecraft will complain when spawning this
    val blocks: Tracked<MutableMap<BlockPos, BlockState>>? = Tracked(mutableMapOf())

    override fun init() {
        blocks?.subscribe(this::buildShape)
    }

    override fun buildShape() {
        detachAll()

        val phys = Physics.physics!!

        if (blocks == null) return

        for (item in blocks.get()) {
            val pos = item.key
            val state = item.value
            val box = state.getCollisionShape(world, pos).boundingBox
            val width = box.maxX - box.minX
            val height = box.maxY - box.minY
            val depth = box.maxZ - box.minZ
            val geom = PxBoxGeometry((width / 2).toFloat(), (height / 2).toFloat(), (depth / 2).toFloat())
            val shape = phys.createShape(geom, Physics.material, true, Physics.shapeFlags)

            shape.localPose.pos = pos.toPx()
            attach(shape)
        }
    }

    override fun initDataTracker() {

    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        blocks?.updateSilently { it.clear(); it }

        val list = nbt.getList("blocks", NbtElement.COMPOUND_TYPE.toInt()) ?: return
        val reg = Registries.BLOCK as SimpleDefaultedRegistry<Block>
        reg.frozen = false // HACK, BAD, THIS IS TERRIBLE
        val lookup = reg.createMutableEntryLookup()

        for (rawItem in list) {
            val item = rawItem as NbtCompound
            val pos = NbtHelper.toBlockPos(item.getCompound("pos"))
            val state = NbtHelper.toBlockState(lookup, item.getCompound("state"))

            blocks?.updateSilently { it[pos] = state; it }
        }

        reg.frozen = true
        blocks?.submit()
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        val list = NbtList()

        if (blocks == null) return

        for (item in blocks.get()) {
            val compound = NbtCompound()

            compound.put("pos", NbtHelper.fromBlockPos(item.key))
            compound.put("state", NbtHelper.fromBlockState(item.value))

            list.add(compound)
        }

        nbt.put("blocks", list)
    }
}
