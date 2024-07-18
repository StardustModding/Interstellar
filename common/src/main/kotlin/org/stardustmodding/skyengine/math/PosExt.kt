package org.stardustmodding.skyengine.math

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.Vec3i
import net.minecraft.world.phys.Vec3

object PosExt {
    fun BlockPos.toVec() = Vec3i(x, y, z)
    fun BlockPos.toVec3d(): Vec3 = Vec3(x.toDouble(), y.toDouble(), z.toDouble())

    fun BlockPos.getRelative(dir: Direction): BlockPos {
        return when (dir) {
            Direction.UP -> offset(0, 1, 0)
            Direction.DOWN -> offset(0, -1, 0)
            Direction.NORTH -> offset(0, 0, -1)
            Direction.EAST -> offset(1, 0, 0)
            Direction.SOUTH -> offset(0, 0, 1)
            Direction.WEST -> offset(-1, 0, 0)
        }
    }

    fun Vec3.toBlockPos(): BlockPos = BlockPos(x.toInt(), y.toInt(), z.toInt())
}
