package org.stardustmodding.interstellar.impl.planet

import net.minecraft.block.Blocks
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.world.biome.source.FixedBiomeSource
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.chunk.NoiseChunkGenerator
import net.minecraft.world.gen.surfacebuilder.MaterialRules
import net.minecraft.world.gen.surfacebuilder.MaterialRules.MaterialRule
import org.stardustmodding.interstellar.api.builder.ChunkGeneratorSettingsBuilder
import org.stardustmodding.interstellar.api.builder.DimensionTypeBuilder
import org.stardustmodding.interstellar.api.builder.MonsterSettingsBuilder
import org.stardustmodding.interstellar.api.planet.Planet
import org.stardustmodding.interstellar.api.planet.PlanetSettings
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.init.Biomes

// This is a moon, but I'm putting it here anyway and YOU CAN'T STOP ME HAHAHA
class Moon : Planet() {
    override val dimensionType =
        DimensionTypeBuilder()
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

    override val generatorSettings = ChunkGeneratorSettingsBuilder().surfaceRule(getMaterialRules()).build()

    override val chunkGenerator = NoiseChunkGenerator(
        FixedBiomeSource(Biomes.MOON_PLAINS!!),
        RegistryEntry.of(generatorSettings)
    )

    override val location = Interstellar.id("moon")

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

    override val settings = PlanetSettings.fromConfig(Interstellar.config!!.planets.planets.find {
        it.name == Interstellar.id("moon").toString()
    }!!)
}
