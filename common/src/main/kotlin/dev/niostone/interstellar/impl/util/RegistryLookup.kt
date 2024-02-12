package dev.niostone.interstellar.impl.util

import net.minecraft.core.MappedRegistry
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
import net.minecraft.server.MinecraftServer
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.levelgen.DensityFunction
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver

object RegistryLookup {
    var ACCESS: RegistryAccess.Frozen? = null
    var BIOMES: MappedRegistry<Biome>? = null
    var CARVERS: MappedRegistry<ConfiguredWorldCarver<*>>? = null
    var DENSITY_FUNCTIONS: MappedRegistry<DensityFunction>? = null

    fun init(server: MinecraftServer) {
        ACCESS = server.registryAccess()
        BIOMES = ACCESS?.registryOrThrow(Registries.BIOME) as MappedRegistry<Biome>
        CARVERS = ACCESS?.registryOrThrow(Registries.CONFIGURED_CARVER) as MappedRegistry<ConfiguredWorldCarver<*>>
        DENSITY_FUNCTIONS = ACCESS?.registryOrThrow(Registries.DENSITY_FUNCTION) as MappedRegistry<DensityFunction>

        // Hacky but whatever
        // TODO: Not do this
        BIOMES?.frozen = false
        CARVERS?.frozen = false
        DENSITY_FUNCTIONS?.frozen = false
    }
}
