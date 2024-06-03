package org.stardustmodding.interstellar.impl.util

import net.minecraft.block.Block
import net.minecraft.registry.DynamicRegistryManager
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.SimpleRegistry
import net.minecraft.server.MinecraftServer
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.carver.ConfiguredCarver
import net.minecraft.world.gen.densityfunction.DensityFunction

object RegistryLookup {
    private var ACCESS: DynamicRegistryManager? = null

    var BIOMES: SimpleRegistry<Biome>? = null
    var CARVERS: SimpleRegistry<ConfiguredCarver<*>>? = null
    var DENSITY_FUNCTIONS: SimpleRegistry<DensityFunction>? = null
    var BLOCKS: SimpleRegistry<Block>? = null

    fun init(server: MinecraftServer) {
        ACCESS = server.registryManager
        BIOMES = ACCESS?.get(RegistryKeys.BIOME) as SimpleRegistry<Biome>
        CARVERS = ACCESS?.get(RegistryKeys.CONFIGURED_CARVER) as SimpleRegistry<ConfiguredCarver<*>>
        DENSITY_FUNCTIONS = ACCESS?.get(RegistryKeys.DENSITY_FUNCTION) as SimpleRegistry<DensityFunction>
        BLOCKS = ACCESS?.get(RegistryKeys.BLOCK) as SimpleRegistry<Block>

        // Hacky but whatever
        // TODO: Not do this
        BIOMES?.frozen = false
        CARVERS?.frozen = false
        DENSITY_FUNCTIONS?.frozen = false
        BLOCKS?.frozen = false
    }

    fun finish() {
        BIOMES?.freeze()
        CARVERS?.freeze()
        DENSITY_FUNCTIONS?.freeze()
    }
}
