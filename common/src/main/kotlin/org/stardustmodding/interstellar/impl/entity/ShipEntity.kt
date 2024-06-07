package org.stardustmodding.interstellar.impl.entity

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
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

@Suppress("RedundantNullableReturnType")
class ShipEntity(type: EntityType<*>, world: World) : Entity(type, world) {
    // The blocks map *has* to be nullable (so we can do the checks without IDEA yelling at us),
    // or Minecraft will complain when spawning this
    val blocks: Tracked<MutableMap<BlockPos, BlockState>>? = Tracked(mutableMapOf())

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
