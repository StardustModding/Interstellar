package org.stardustmodding.interstellar.impl.init

import net.minecraft.registry.Registry
import net.minecraft.server.MinecraftServer
import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry
import org.stardustmodding.interstellar.api.init.InitializedServer
import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.planet.Moon

object Planets : InitializedServer {
    val MOON = Registry.registerReference(InterstellarRegistries.PLANETS, Interstellar.id("moon"), Moon()).key.get()

    override fun init(it: MinecraftServer) {
        val registry = DynamicDimensionRegistry.from(it)

        Moon().create(registry)
    }
}
