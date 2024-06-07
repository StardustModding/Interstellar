package org.stardustmodding.interstellar.api.block

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

object BlockUtil {
    @JvmStatic
    fun getBlockBounds(state: BlockState, world: BlockView, pos: BlockPos): BlockBounds {
        val box = state.getCollisionShape(world, pos).boundingBox
        val width = box.maxX - box.minX
        val height = box.maxY - box.minY
        val depth = box.maxZ - box.minZ

        return BlockBounds(width.toFloat(), height.toFloat(), depth.toFloat())
    }
}