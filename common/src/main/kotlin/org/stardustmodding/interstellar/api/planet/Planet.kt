package org.stardustmodding.interstellar.api.planet

import net.minecraft.util.Identifier
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings
import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry

abstract class Planet {
    abstract val dimensionType: DimensionType
    abstract val chunkGenerator: ChunkGenerator
    abstract val generatorSettings: ChunkGeneratorSettings
    abstract val location: Identifier
    abstract val settings: PlanetSettings

    fun create(registry: DynamicDimensionRegistry) {
        registry.loadDynamicDimension(location, chunkGenerator, dimensionType)
    }
}
