package org.stardustmodding.interstellar.impl.entity

import net.minecraft.core.BlockPos
import net.minecraft.core.DefaultedMappedRegistry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.nbt.Tag
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import org.stardustmodding.interstellar.api.data.Tracked

@Suppress("RedundantNullableReturnType")
class ShipEntity(type: EntityType<*>, world: Level) : Entity(type, world) {
    // The blocks map *has* to be nullable (so we can do the checks without IDEA yelling at us),
    // or Minecraft will complain when spawning this
    val blocks: Tracked<MutableMap<BlockPos, BlockState>>? = Tracked(mutableMapOf(
        BlockPos(-1, 0, 0) to Blocks.OBSIDIAN.defaultBlockState(),
        BlockPos(-1, 1, 0) to Blocks.OBSIDIAN.defaultBlockState(),
        BlockPos(-1, 2, 0) to Blocks.OBSIDIAN.defaultBlockState(),
        BlockPos(0, 0, 0) to Blocks.OBSIDIAN.defaultBlockState(),
        BlockPos(0, 1, 0) to Blocks.OBSIDIAN.defaultBlockState(),
        BlockPos(0, 2, 0) to Blocks.OBSIDIAN.defaultBlockState(),
        BlockPos(1, 0, 0) to Blocks.OBSIDIAN.defaultBlockState(),
        BlockPos(1, 1, 0) to Blocks.OBSIDIAN.defaultBlockState(),
        BlockPos(1, 2, 0) to Blocks.OBSIDIAN.defaultBlockState(),
    ))

    override fun defineSynchedData() {

    }

    override fun readAdditionalSaveData(nbt: CompoundTag) {
        blocks?.updateSilently { it.clear(); it }

        val list = nbt.getList("blocks", Tag.TAG_COMPOUND.toInt()) ?: return
        val reg = BuiltInRegistries.BLOCK as DefaultedMappedRegistry<Block>
        reg.frozen = false // HACK, BAD, THIS IS TERRIBLE
        val lookup = reg.createRegistrationLookup()

        for (rawItem in list) {
            val item = rawItem as CompoundTag
            val pos = NbtUtils.readBlockPos(item.getCompound("pos"))
            val state = NbtUtils.readBlockState(lookup, item.getCompound("state"))

            blocks?.updateSilently { it[pos] = state; it }
        }

        reg.frozen = true
        blocks?.submit()
    }

    override fun addAdditionalSaveData(nbt: CompoundTag) {
        val list = ListTag()

        if (blocks == null) return

        for (item in blocks.get()) {
            val compound = CompoundTag()

            compound.put("pos", NbtUtils.writeBlockPos(item.key))
            compound.put("state", NbtUtils.writeBlockState(item.value))

            list.add(compound)
        }

        nbt.put("blocks", list)
    }
}
