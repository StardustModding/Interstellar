package org.stardustmodding.interstellar.impl.init

import net.minecraft.server.MinecraftServer
import org.stardustmodding.interstellar.api.init.InitializedServer
import org.stardustmodding.interstellar.impl.util.RegistryLookup

object Initializer: InitializedServer {
    override fun init(it: MinecraftServer) {
        initChild(it, RegistryLookup::class)
        initChild(it, Biomes::class)
        initChild(it, Planets::class)

        RegistryLookup.finish()
    }
}
