package org.stardustmodding.interstellar.impl.init

import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry
import org.stardustmodding.interstellar.impl.planet.Moon
import net.minecraft.server.MinecraftServer

object Planets {
    fun register(server: MinecraftServer) {
        val registry = server as DynamicDimensionRegistry

        Moon().create(registry)
    }
}
