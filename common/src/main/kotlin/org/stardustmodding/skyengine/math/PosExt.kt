package org.stardustmodding.skyengine.math

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i

object PosExt {
    fun BlockPos.toVec() = Vec3i(x, y, z)
}