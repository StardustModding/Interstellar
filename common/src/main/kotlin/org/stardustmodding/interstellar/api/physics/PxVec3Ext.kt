package org.stardustmodding.interstellar.api.physics

import net.minecraft.util.math.Vec3d
import physx.common.PxVec3

object PxVec3Ext {
    fun PxVec3.toVec3d(): Vec3d {
        return Vec3d(this.x.toDouble(), this.y.toDouble(), this.z.toDouble())
    }
}