package dev.niostone.interstellar.impl.planet

import dev.niostone.interstellar.api.builder.DimensionTypeBuilder
import dev.niostone.interstellar.api.builder.MonsterSettingsBuilder
import dev.niostone.interstellar.impl.Interstellar
import dev.niostone.interstellar.impl.util.RegistryLookup
import dev.niostone.interstellar.impl.init.Biomes
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.level.biome.FixedBiomeSource
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.ChunkGenerator
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.levelgen.*

// This is a moon, but I'm putting it here anyway and YOU CAN'T STOP ME HAHAHA
class Moon: Planet {
    override fun getDimensionType(): DimensionType {
        return DimensionTypeBuilder()
                .ultraWarm(false)
                .natural(false)
                .respawnAnchorWorks(false)
                .bedWorks(false)
                .hasSkyLight(true)
                .hasCeiling(false)
                .coordinateScale(1.0)
                .ambientLight(4.0f)
                .logicalHeight(384)
                .effectsLocation(ResourceLocation("minecraft", "overworld"))
                .infiniburn(TagKey.create(Registries.BLOCK, ResourceLocation(Interstellar.MOD_ID, "infiniburn_moon")))
                .minY(-64)
                .height(384)
                .monsterSettings(MonsterSettingsBuilder()
                        .piglinSafe(true)
                        .hasRaids(false)
                        .blockLightLimit(0)
                        .lightTest(UniformInt.of(0, 4))
                        .build())
                .build()
    }

    override fun getChunkGenerator(): ChunkGenerator {
        return NoiseBasedChunkGenerator(
                FixedBiomeSource(Holder.Direct(Biomes.MOON_PLAINS!!)),
                Holder.direct(this.getGeneratorSettings())
        )
    }

    override fun getGeneratorSettings(): NoiseGeneratorSettings {
        return NoiseGeneratorSettings(
                NoiseSettings.create(-64, 384, 1, 2),
                Blocks.STONE.defaultBlockState(), // defaultBlock
                Blocks.AIR.defaultBlockState(), // defaultFluid
                NoiseRouter(
                        DensityFunctions.constant(0.0), // barrier
                        DensityFunctions.constant(0.0), // fluidLevelFloodedness
                        DensityFunctions.constant(0.0), // fluidLevelSpread
                        DensityFunctions.constant(0.0), // lava
                        DensityFunctions.constant(0.0), // temperature
                        DensityFunctions.constant(0.0), // vegetation
                        DensityFunctions.constant(0.0), // crontinents
                        DensityFunctions.constant(0.0), // erosion
                        DensityFunctions.constant(0.0), // depth
                        DensityFunctions.constant(0.0), // ridges
                        DensityFunctions.constant(0.0), // initialDensityWithoutJaggedness
                        DensityFunctions.interpolated(
                            RegistryLookup.DENSITY_FUNCTIONS?.get(
                                ResourceLocation("minecraft", "overworld/base_3d_noise")
                            )!!
                        ), // finalDensity
                        DensityFunctions.constant(0.0), // veinToggle
                        DensityFunctions.constant(0.0), // veinRidged
                        DensityFunctions.constant(0.0) // veinGap
                ),
                SurfaceRules.bandlands(), // surfaceRule
                mutableListOf(), // spawnTarget
                0, // seaLevel
                true, // disableMobGeneration
                false, // aquifersEnabled
                true, // oreVeinsEnabled
                false // legacyRandomSource
        )
    }

    override fun getLocation(): ResourceLocation {
        return ResourceLocation(Interstellar.MOD_ID, "moon")
    }
}
