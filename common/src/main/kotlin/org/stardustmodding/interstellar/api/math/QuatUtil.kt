package org.stardustmodding.interstellar.api.math

import net.minecraft.util.math.MathHelper
import physx.common.PxQuat

object QuatUtil {
    // https://github.com/jMonkeyEngine/jmonkeyengine/blob/v3.6.1-stable/jme3-core/src/main/java/com/jme3/math/Quaternion.java#L283
    fun fromAngles(yaw: Float, roll: Float, pitch: Float): PxQuat {
        val sinRoll: Float
        val sinPitch: Float
        val sinYaw: Float
        val cosRoll: Float
        val cosPitch: Float
        val cosYaw: Float

        var angle = pitch * 0.5f

        sinPitch = MathHelper.sin(angle)
        cosPitch = MathHelper.cos(angle)

        angle = roll * 0.5f

        sinRoll = MathHelper.sin(angle)
        cosRoll = MathHelper.cos(angle)

        angle = yaw * 0.5f

        sinYaw = MathHelper.sin(angle)
        cosYaw = MathHelper.cos(angle)

        // variables used to reduce multiplication calls.
        val cosRollXcosPitch = cosRoll * cosPitch
        val sinRollXsinPitch = sinRoll * sinPitch
        val cosRollXsinPitch = cosRoll * sinPitch
        val sinRollXcosPitch = sinRoll * cosPitch

        val w = (cosRollXcosPitch * cosYaw - sinRollXsinPitch * sinYaw)
        val x = (cosRollXcosPitch * sinYaw + sinRollXsinPitch * cosYaw)
        val y = (sinRollXcosPitch * cosYaw + cosRollXsinPitch * sinYaw)
        val z = (cosRollXsinPitch * cosYaw - sinRollXcosPitch * sinYaw)

        return PxQuat(x, y, z, w).normalized
    }
}