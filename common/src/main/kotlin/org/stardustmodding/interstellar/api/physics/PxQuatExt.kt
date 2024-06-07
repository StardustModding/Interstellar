package org.stardustmodding.interstellar.api.physics

import net.minecraft.util.math.MathHelper
import physx.common.PxQuat
import physx.common.PxVec3

object PxQuatExt {
    // https://github.com/jMonkeyEngine/jmonkeyengine/blob/v3.6.1-stable/jme3-core/src/main/java/com/jme3/math/Quaternion.java#L328
    fun PxQuat.toEulerAngles(): PxVec3 {
        // Unfortunately, Minecraft's `MathHelper` class uses `Double`s instead of `Float`s,
        // so we may lose precision here. :'(

        val vec = PxVec3()
        val sqw = w * w
        val sqx = x * x
        val sqy = y * y
        val sqz = z * z
        val unit = sqx + sqy + sqz + sqw // if normalized is one, otherwise

        // is correction factor
        val test = x * y + z * w
        if (test > 0.499 * unit) { // singularity at north pole
            vec.y = (2 * MathHelper.atan2(x.toDouble(), w.toDouble())).toFloat()
            vec.z = MathHelper.HALF_PI
            vec.x = 0f
        } else if (test < -0.499 * unit) { // singularity at south pole
            vec.y = (-2 * MathHelper.atan2(x.toDouble(), w.toDouble())).toFloat()
            vec.z = -MathHelper.HALF_PI
            vec.x = 0f
        } else {
            vec.y = MathHelper.atan2((2 * y * w - 2 * x * z).toDouble(), (sqx - sqy - sqz + sqw).toDouble()).toFloat() // roll or heading
            vec.z = Math.asin((2 * test / unit).toDouble()).toFloat() // pitch or attitude
            vec.x =
                MathHelper.atan2((2 * x * w - 2 * y * z).toDouble(), (-sqx + sqy - sqz + sqw).toDouble()).toFloat() // yaw or bank
        }

        return vec
    }
}