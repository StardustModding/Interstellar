package org.stardustmodding.interstellar.api.planet

import net.minecraft.util.Identifier
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings
import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry

interface Planet {
    fun getDimensionType(): DimensionType
    fun getChunkGenerator(): ChunkGenerator
    fun getGeneratorSettings(): ChunkGeneratorSettings
    fun getLocation(): Identifier

    fun create(registry: DynamicDimensionRegistry) {
        registry.loadDynamicDimension(getLocation(), getChunkGenerator(), getDimensionType())
    }
}
