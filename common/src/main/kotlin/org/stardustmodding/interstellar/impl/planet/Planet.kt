package org.stardustmodding.interstellar.impl.planet

import dev.galacticraft.dynamicdimensions.api.DynamicDimensionRegistry
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Identifier
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings

interface Planet {
    fun getDimensionType(): DimensionType
    fun getChunkGenerator(): ChunkGenerator
    fun getGeneratorSettings(): ChunkGeneratorSettings
    fun getLocation(): Identifier

    fun create(registry: DynamicDimensionRegistry) {
        registry.loadDynamicDimension(getLocation() as ResourceLocation, getChunkGenerator() as net.minecraft.world.level.chunk.ChunkGenerator, getDimensionType() as net.minecraft.world.level.dimension.DimensionType)
    }
}
