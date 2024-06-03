package org.stardustmodding.interstellar.impl.init

import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.server.MinecraftServer
import net.minecraft.world.biome.Biome
import org.stardustmodding.interstellar.api.init.InitializedServer
import org.stardustmodding.interstellar.impl.util.RegistryLookup
import org.stardustmodding.interstellar.impl.world.biome.MoonPlains

object Biomes: InitializedServer {
    var MOON_PLAINS: RegistryEntry.Reference<Biome>? = null

    override fun init(it: MinecraftServer) {
        MOON_PLAINS = Registry.registerReference(
            RegistryLookup.BIOMES!!,
            MoonPlains().getLocation(),
            MoonPlains().getBiome()
        )
    }
}
