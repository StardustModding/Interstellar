package org.stardustmodding.interstellar.api.builder

import net.minecraft.util.Identifier
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes
import net.minecraft.world.gen.noise.NoiseRouter
import org.stardustmodding.interstellar.impl.util.RegistryLookup

class NoiseRouterBuilder {
    private var barrier = 0.0
    private var fluidLevelFloodedness = 0.0
    private var fluidLevelSpread = 0.0
    private var lava = 0.0
    private var temperature = 0.0
    private var vegetation = 0.0
    private var crontinents = 0.0
    private var erosion = 0.0
    private var depth = 0.0
    private var ridges = 0.0
    private var initialDensityWithoutJaggedness = 0.0
    private var finalDensity = Identifier("minecraft", "overworld/base_3d_noise")
    private var veinToggle = 0.0
    private var veinRidged = 0.0
    private var veinGap = 0.0

    fun barrier(value: Double) = apply { this.barrier = value }
    fun fluidLevelFloodedness(value: Double) = apply { this.fluidLevelFloodedness = value }
    fun fluidLevelSpread(value: Double) = apply { this.fluidLevelSpread = value }
    fun lava(value: Double) = apply { this.lava = value }
    fun temperature(value: Double) = apply { this.temperature = value }
    fun vegetation(value: Double) = apply { this.vegetation = value }
    fun crontinents(value: Double) = apply { this.crontinents = value }
    fun erosion(value: Double) = apply { this.erosion = value }
    fun depth(value: Double) = apply { this.depth = value }
    fun ridges(value: Double) = apply { this.ridges = value }
    fun initialDensityWithoutJaggedness(value: Double) = apply { this.initialDensityWithoutJaggedness = value }
    fun finalDensity(value: Identifier) = apply { this.finalDensity = value }
    fun veinToggle(value: Double) = apply { this.veinToggle = value }
    fun veinRidged(value: Double) = apply { this.veinRidged = value }
    fun veinGap(value: Double) = apply { this.veinGap = value }

    fun build() = NoiseRouter(
        DensityFunctionTypes.constant(barrier),
        DensityFunctionTypes.constant(fluidLevelFloodedness),
        DensityFunctionTypes.constant(fluidLevelSpread),
        DensityFunctionTypes.constant(lava),
        DensityFunctionTypes.constant(temperature),
        DensityFunctionTypes.constant(vegetation),
        DensityFunctionTypes.constant(crontinents),
        DensityFunctionTypes.constant(erosion),
        DensityFunctionTypes.constant(depth),
        DensityFunctionTypes.constant(ridges),
        DensityFunctionTypes.constant(initialDensityWithoutJaggedness),
        DensityFunctionTypes.interpolated(
            RegistryLookup.DENSITY_FUNCTIONS?.get(
                finalDensity
            )!!
        ),
        DensityFunctionTypes.constant(veinToggle),
        DensityFunctionTypes.constant(veinRidged),
        DensityFunctionTypes.constant(veinGap)
    )
}