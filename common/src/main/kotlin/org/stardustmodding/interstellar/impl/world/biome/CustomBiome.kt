package org.stardustmodding.interstellar.impl.world.biome

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome

interface CustomBiome {
    fun getBiome(): Biome
    fun getLocation(): ResourceLocation
}