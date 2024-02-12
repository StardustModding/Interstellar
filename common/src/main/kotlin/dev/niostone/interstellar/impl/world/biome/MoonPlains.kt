package dev.niostone.interstellar.impl.world.biome

import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.biome.BiomeGenerationSettings
import net.minecraft.world.level.biome.BiomeSpecialEffects
import net.minecraft.world.level.biome.MobSpawnSettings

object MoonPlains {
    val BIOME: Biome = Biome.BiomeBuilder()
            .temperature(0.0f)
            .downfall(0.0f)
            .hasPrecipitation(false)
            .temperatureAdjustment(Biome.TemperatureModifier.FROZEN)
            .specialEffects(BiomeSpecialEffects.Builder()
                    .skyColor(0)
                    .fogColor(1710618)
                    .waterColor(9219048)
                    .waterFogColor(9079551)
                    .grassColorOverride(6513507)
                    .foliageColorOverride(7895160)
                    .build())
            .generationSettings(BiomeGenerationSettings.PlainBuilder()
//                    .addCarver(GenerationStep.Carving.AIR, RegistryLookup.CARVERS.getHolderOrThrow(Carvers.CAVE))
                    .build())
            .mobSpawnSettings(MobSpawnSettings.Builder().build())
            .build()
}
