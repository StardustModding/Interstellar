package org.stardustmodding.interstellar.impl.init

import net.minecraft.server.MinecraftServer
import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry
import org.stardustmodding.interstellar.api.init.InitializedServer
import org.stardustmodding.interstellar.impl.planet.Moon

object Planets: InitializedServer {
    override fun init(it: MinecraftServer) {
        val registry = DynamicDimensionRegistry.from(it)

        Moon().create(registry)
    }
}
