package org.stardustmodding.interstellar.impl.init

import net.minecraft.server.MinecraftServer
import org.stardustmodding.interstellar.api.init.InitializedServer
import org.stardustmodding.interstellar.impl.util.RegistryLookup

object Initializer : InitializedServer {
    override fun init(it: MinecraftServer) {
        initChild(it, RegistryLookup)
        initChild(it, Biomes)
        initChild(it, Planets)

        RegistryLookup.finish()
    }
}
