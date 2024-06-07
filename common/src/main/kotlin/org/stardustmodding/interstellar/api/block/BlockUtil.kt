package org.stardustmodding.interstellar.api.block

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView

object BlockUtil {
    @JvmStatic
    fun getBlockBounds(state: BlockState, world: BlockView, pos: BlockPos): BlockBounds {
        val shape = state.getCollisionShape(world, pos)

        if (shape.isEmpty) {
            return BlockBounds(1f, 1f, 1f)
        }

        val box = shape.boundingBox
        val width = box.maxX - box.minX
        val height = box.maxY - box.minY
        val depth = box.maxZ - box.minZ

        return BlockBounds(width.toFloat(), height.toFloat(), depth.toFloat())
    }
}