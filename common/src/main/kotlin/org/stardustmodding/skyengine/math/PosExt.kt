package org.stardustmodding.skyengine.math

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i

object PosExt {
    fun BlockPos.toVec() = Vec3i(x, y, z)
    fun BlockPos.toVec3d(): Vec3d = Vec3d(x.toDouble(), y.toDouble(), z.toDouble())

    fun BlockPos.getRelative(dir: Direction): BlockPos {
        return when (dir) {
            Direction.UP -> add(0, 1, 0)
            Direction.DOWN -> add(0, -1, 0)
            Direction.NORTH -> add(0, 0, -1)
            Direction.EAST -> add(1, 0, 0)
            Direction.SOUTH -> add(0, 0, 1)
            Direction.WEST -> add(-1, 0, 0)
        }
    }

    fun Vec3d.toBlockPos(): BlockPos = BlockPos(x.toInt(), y.toInt(), z.toInt())
}