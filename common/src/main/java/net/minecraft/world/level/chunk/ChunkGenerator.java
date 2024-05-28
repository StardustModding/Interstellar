package net.minecraft.world.level.chunk;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

public class ChunkGenerator extends NoiseChunkGenerator {
    public ChunkGenerator(BiomeSource biomeSource, RegistryEntry<ChunkGeneratorSettings> settings) {
        super(biomeSource, settings);
    }
}
