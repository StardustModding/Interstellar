package org.stardustmodding.interstellar.api.math

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import physx.common.PxVec3

object McExtensions {
    fun Vec3d.set(other: Vec3d) {
        multiply(0.0)
        add(other)
    }

    fun Vec3d.toPx(): PxVec3 {
        return PxVec3(x.toFloat(), y.toFloat(), z.toFloat())
    }

    fun BlockPos.toPx(): PxVec3 {
        return PxVec3(x.toFloat(), y.toFloat(), z.toFloat())
    }
}