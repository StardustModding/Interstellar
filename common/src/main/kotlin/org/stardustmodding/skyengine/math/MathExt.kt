package org.stardustmodding.skyengine.math

import net.minecraft.util.Mth
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.tan

object MathExt {
    fun Float.sin() = Mth.sin(this)
    fun Float.cos() = Mth.cos(this)
    fun Float.tan() = tan(this)
    fun Float.asin() = asin(this)
    fun Float.acos() = acos(this)
    fun Float.atan() = atan(this)
}
