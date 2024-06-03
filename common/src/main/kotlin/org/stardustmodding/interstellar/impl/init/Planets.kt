package org.stardustmodding.interstellar.impl.init

import net.minecraft.server.MinecraftServer
import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry
import org.stardustmodding.interstellar.impl.planet.Moon

object Planets {
    fun register(server: MinecraftServer) {
        val registry = DynamicDimensionRegistry.from(server)

        Moon().create(registry)
    }
}
