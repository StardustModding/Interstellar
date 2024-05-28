package org.stardustmodding.interstellar.impl.world.biome

import net.minecraft.util.Identifier
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeEffects
import net.minecraft.world.biome.GenerationSettings
import net.minecraft.world.biome.SpawnSettings
import org.stardustmodding.interstellar.impl.Interstellar

class MoonPlains : CustomBiome {
    override fun getBiome(): Biome {
        return Biome.Builder()
            .temperature(0.0f)
            .downfall(0.0f)
            .precipitation(false)
            .temperatureModifier(Biome.TemperatureModifier.FROZEN)
            .effects(
                BiomeEffects.Builder()
                    .skyColor(0)
                    .fogColor(1710618)
                    .waterColor(9219048)
                    .waterFogColor(9079551)
                    .grassColor(6513507)
                    .foliageColor(7895160)
                    .build()
            )
            .generationSettings(
                GenerationSettings.Builder()
//                   .addCarver(GenerationStep.Carving.AIR, RegistryLookup.CARVERS.getHolderOrThrow(Carvers.CAVE))
                   .build()
            )
            .spawnSettings(SpawnSettings.Builder().build())
            .build()
    }

    override fun getLocation(): Identifier {
        return Interstellar.id("moon_plains")
    }
}
