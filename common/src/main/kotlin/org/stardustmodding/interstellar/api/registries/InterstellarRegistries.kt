package org.stardustmodding.interstellar.api.registries

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleDefaultedRegistry
import org.stardustmodding.interstellar.api.gas.Gas
import org.stardustmodding.interstellar.api.planet.Planet
import org.stardustmodding.interstellar.api.starsystem.StarSystem
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.Interstellar.id

object InterstellarRegistries {
    // Keys

    private val PLANETS_KEY = RegistryKey.ofRegistry<Planet>(Interstellar.id("planets"))!!
    private val STAR_SYSTEMS_KEY = RegistryKey.ofRegistry<StarSystem>(Interstellar.id("star_systems"))!!
    private val GASES_KEY = RegistryKey.ofRegistry<Gas>(Interstellar.id("gases"))!!

    // Registries

    val PLANETS = SimpleDefaultedRegistry(id("planet").toString(), PLANETS_KEY, Lifecycle.experimental(), false)
    val STAR_SYSTEMS = SimpleDefaultedRegistry(id("star_system").toString(), STAR_SYSTEMS_KEY, Lifecycle.experimental(), false)
    val GASES = SimpleDefaultedRegistry(id("gas").toString(), GASES_KEY, Lifecycle.experimental(), false)
}
