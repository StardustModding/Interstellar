package org.stardustmodding.ionengine.block

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import org.stardustmodding.ionengine.buffer.EnergyBuffered
import org.stardustmodding.ionengine.operation.EnergyOperation

open class PoweredBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : BlockEntity(type, pos, state), EnergyBuffered {
    override var buffer = 0f
    override val operationQueue = mutableListOf<EnergyOperation>()

    override fun load(tag: CompoundTag) {
        load(tag.getList("queue", Tag.TAG_STRING.toInt()))
    }

    override fun saveAdditional(nbt: CompoundTag) {
        nbt.put("queue", save())
    }
}
