package org.stardustmodding.interstellar.api.world

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

object WorldExt {
    fun World.getBlocksInRange(pos: Vec3d, range: Int): Map<BlockPos, BlockState> {
        return getBlocksInRange(BlockPos(pos.x.toInt(), pos.y.toInt(), pos.z.toInt()), range)
    }

    fun World.getBlocksInRange(pos: BlockPos, range: Int): Map<BlockPos, BlockState> {
        val list = mutableMapOf<BlockPos, BlockState>()
        val half = range / 2

        for (x in pos.x - half..pos.x + half) {
            for (y in pos.y - half..pos.y + half) {
                if (y > topY || y < bottomY) continue

                for (z in pos.z - half..pos.z + half) {
                    val loc = BlockPos(x, y, z)
                    val block = getBlockState(loc)

                    if (!block.isAir && block.block != Blocks.AIR) list[loc] = block
                }
            }
        }

        return list
    }
}