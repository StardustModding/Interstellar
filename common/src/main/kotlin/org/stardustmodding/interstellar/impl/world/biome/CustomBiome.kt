package org.stardustmodding.interstellar.impl.world.biome

import net.minecraft.util.Identifier
import net.minecraft.world.biome.Biome

interface CustomBiome {
    fun getBiome(): Biome
    fun getLocation(): Identifier
}
