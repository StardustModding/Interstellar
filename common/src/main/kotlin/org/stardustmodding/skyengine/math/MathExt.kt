package org.stardustmodding.skyengine.math

import net.minecraft.util.math.MathHelper
import kotlin.math.tan
import kotlin.math.asin
import kotlin.math.acos
import kotlin.math.atan

object MathExt {
    fun Float.sin() = MathHelper.sin(this)
    fun Float.cos() = MathHelper.cos(this)
    fun Float.tan() = tan(this)
    fun Float.asin() = asin(this)
    fun Float.acos() = acos(this)
    fun Float.atan() = atan(this)
}
