package org.stardustmodding.interstellar.impl.planet

import net.minecraft.block.Blocks
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.world.biome.source.FixedBiomeSource
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings
import net.minecraft.world.gen.chunk.GenerationShapeConfig
import net.minecraft.world.gen.chunk.NoiseChunkGenerator
import net.minecraft.world.gen.densityfunction.DensityFunctionTypes
import net.minecraft.world.gen.noise.NoiseRouter
import net.minecraft.world.gen.surfacebuilder.MaterialRules
import net.minecraft.world.gen.surfacebuilder.MaterialRules.MaterialRule
import org.stardustmodding.interstellar.api.builder.DimensionTypeBuilder
import org.stardustmodding.interstellar.api.builder.MonsterSettingsBuilder
import org.stardustmodding.interstellar.api.planet.Planet
import org.stardustmodding.interstellar.api.planet.PlanetSettings
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.init.Biomes
import org.stardustmodding.interstellar.impl.util.RegistryLookup

// This is a moon, but I'm putting it here anyway and YOU CAN'T STOP ME HAHAHA
class Moon : Planet {
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
            .effectsLocation(Identifier("minecraft", "overworld"))
            .infiniburn(TagKey.of(RegistryKeys.BLOCK, Interstellar.id("infiniburn_moon")))
            .minY(-64)
            .height(384)
            .monsterSettings(
                MonsterSettingsBuilder()
                    .piglinSafe(true)
                    .hasRaids(false)
                    .blockLightLimit(0)
                    .lightTest(UniformIntProvider.create(0, 4))
                    .build()
            )
            .build()
    }

    override fun getChunkGenerator(): ChunkGenerator {
        return NoiseChunkGenerator(
            FixedBiomeSource(Biomes.MOON_PLAINS!!),
            RegistryEntry.of(this.getGeneratorSettings())
        )
    }

    override fun getGeneratorSettings(): ChunkGeneratorSettings {
        return ChunkGeneratorSettings(
            GenerationShapeConfig.create(-64, 128, 1, 2),
            Blocks.STONE.defaultState, // defaultBlock
            Blocks.AIR.defaultState, // defaultFluid
            NoiseRouter(
                DensityFunctionTypes.constant(0.0), // barrier
                DensityFunctionTypes.constant(0.0), // fluidLevelFloodedness
                DensityFunctionTypes.constant(0.0), // fluidLevelSpread
                DensityFunctionTypes.constant(0.0), // lava
                DensityFunctionTypes.constant(0.0), // temperature
                DensityFunctionTypes.constant(0.0), // vegetation
                DensityFunctionTypes.constant(0.0), // crontinents
                DensityFunctionTypes.constant(0.0), // erosion
                DensityFunctionTypes.constant(0.0), // depth
                DensityFunctionTypes.constant(0.0), // ridges
                DensityFunctionTypes.constant(0.0), // initialDensityWithoutJaggedness
                DensityFunctionTypes.interpolated(
                    RegistryLookup.DENSITY_FUNCTIONS?.get(
                        Identifier("minecraft", "overworld/base_3d_noise")
                    )!!
                ), // finalDensity
                DensityFunctionTypes.constant(0.0), // veinToggle
                DensityFunctionTypes.constant(0.0), // veinRidged
                DensityFunctionTypes.constant(0.0) // veinGap
            ),
            getMaterialRules(), // surfaceRule
            mutableListOf(), // spawnTarget
            0, // seaLevel
            true, // disableMobGeneration
            false, // aquifersEnabled
            true, // oreVeinsEnabled
            false // legacyRandomSource
        )
    }

    override fun getLocation(): Identifier {
        return Interstellar.id("moon")
    }

    private fun getMaterialRules(): MaterialRule {
        val builder = arrayListOf<MaterialRule>()

        builder.add(
            MaterialRules.condition(
                MaterialRules.not(
                    MaterialRules.verticalGradient(
                        "stone_roof",
                        YOffset.belowTop(5),
                        YOffset.getTop()
                    )
                ), MaterialRules.block(Blocks.STONE.defaultState)
            )
        )

        builder.add(
            MaterialRules.condition(
                MaterialRules.verticalGradient(
                    "bedrock_floor",
                    YOffset.getBottom(),
                    YOffset.aboveBottom(5)
                ), MaterialRules.block(Blocks.BEDROCK.defaultState)
            )
        )

        builder.add(
            MaterialRules.condition(
                MaterialRules.verticalGradient(
                    "deepslate",
                    YOffset.fixed(0),
                    YOffset.fixed(8)
                ), MaterialRules.block(Blocks.DEEPSLATE.defaultState)
            )
        )

        return MaterialRules.sequence(*builder.toTypedArray())
    }

    override fun settings(): PlanetSettings {
        return PlanetSettings.fromConfig(Interstellar.config!!.planets.moon)
    }
}
