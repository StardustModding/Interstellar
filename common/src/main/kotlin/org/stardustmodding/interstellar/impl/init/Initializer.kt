package org.stardustmodding.interstellar.impl.init

import net.minecraft.server.MinecraftServer
import org.stardustmodding.interstellar.impl.util.RegistryLookup


object Initializer {
    fun init(server: MinecraftServer) {
        RegistryLookup.init(server)

        Biomes.register()
        Planets.register(server)

        RegistryLookup.finish()
    }
}
