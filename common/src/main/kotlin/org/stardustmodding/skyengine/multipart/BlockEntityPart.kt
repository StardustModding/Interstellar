package org.stardustmodding.skyengine.multipart

import net.minecraft.world.entity.EntityDimensions
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState

class BlockEntityPart(owner: BlockAggregateEntity, val pos: BlockPos, val state: BlockState) :
    EntityPart(owner, "_block_${pos}", calcBlockDimensions(owner.level, pos, state)) {

    companion object {
        fun calcBlockDimensions(world: BlockGetter, pos: BlockPos, state: BlockState): EntityDimensions {
            val bounds = state.getCollisionShape(world, pos).bounds()
            val width = bounds.maxX - bounds.minX
            val height = bounds.maxY - bounds.minY

            return EntityDimensions.scalable(width.toFloat(), height.toFloat())
        }
    }
}