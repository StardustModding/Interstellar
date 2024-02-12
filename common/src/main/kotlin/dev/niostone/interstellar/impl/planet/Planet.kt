package dev.niostone.interstellar.impl.planet

import dev.galacticraft.dynamicdimensions.api.DynamicDimensionRegistry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.chunk.ChunkGenerator
import net.minecraft.world.level.dimension.DimensionType
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings

interface Planet {
    fun getDimensionType(): DimensionType
    fun getChunkGenerator(): ChunkGenerator
    fun getGeneratorSettings(): NoiseGeneratorSettings
    fun getLocation(): ResourceLocation

    fun create(registry: DynamicDimensionRegistry) {
        registry.createDynamicDimension(getLocation(), getChunkGenerator(), getDimensionType());
    }
}
