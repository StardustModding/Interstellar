package org.stardustmodding.ionengine.block

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.util.math.BlockPos
import org.stardustmodding.ionengine.buffer.EnergyBuffered
import org.stardustmodding.ionengine.operation.EnergyOperation

open class PoweredBlockEntity(type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?) : BlockEntity(type, pos, state), EnergyBuffered {
    override var buffer = 0f
    override val operationQueue = mutableListOf<EnergyOperation>()

    override fun readNbt(nbt: NbtCompound) {
        load(nbt.getList("queue", NbtElement.STRING_TYPE.toInt()))
    }

    override fun writeNbt(nbt: NbtCompound) {
        nbt.put("queue", save())
    }
}
