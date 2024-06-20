package org.stardustmodding.skyengine.multipart

import net.minecraft.block.BlockState
import net.minecraft.entity.EntityDimensions
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

class BlockEntityPart(owner: BlockAggregateEntity, val pos: BlockPos, val state: BlockState) :
    EntityPart(owner, "_block_${pos}", calcBlockDimensions(owner.world, pos, state)) {

    companion object {
        fun calcBlockDimensions(world: BlockView, pos: BlockPos, state: BlockState): EntityDimensions {
            val bounds = state.getCollisionShape(world, pos).boundingBox
            val width = bounds.maxX - bounds.minX
            val height = bounds.maxY - bounds.minY

            return EntityDimensions.changing(width.toFloat(), height.toFloat())
        }
    }
}