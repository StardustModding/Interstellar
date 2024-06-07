package org.stardustmodding.interstellar.api.math

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import physx.common.PxVec3

object McExtensions {
    fun Vec3d.toPx(): PxVec3 {
        return PxVec3(x.toFloat(), y.toFloat(), z.toFloat())
    }

    fun Vec3d.clone(): Vec3d {
        return Vec3d(x, y, z)
    }

    fun Vec3d.eq(other: Vec3d) = x == other.x && y == other.y && z == other.z

    fun BlockPos.toPx(): PxVec3 {
        return PxVec3(x.toFloat(), y.toFloat(), z.toFloat())
    }
}