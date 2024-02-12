package dev.niostone.interstellar.impl.init

import dev.galacticraft.dynamicdimensions.api.DynamicDimensionRegistry
import dev.niostone.interstellar.impl.planet.Moon
import net.minecraft.server.MinecraftServer

object Planets {
    fun register(server: MinecraftServer) {
        val registry = server as DynamicDimensionRegistry

        Moon().create(registry)
    }
}
