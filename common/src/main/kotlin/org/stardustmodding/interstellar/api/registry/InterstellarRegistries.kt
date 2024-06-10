package org.stardustmodding.interstellar.api.registry

import com.mojang.serialization.Lifecycle
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.SimpleDefaultedRegistry
import org.stardustmodding.interstellar.api.gas.Gas
import org.stardustmodding.interstellar.api.planet.Planet
import org.stardustmodding.interstellar.api.planet.PlanetSettings
import org.stardustmodding.interstellar.api.starsystem.StarSystem
import org.stardustmodding.interstellar.impl.Interstellar.id

object InterstellarRegistries {
    object Keys {
        val PLANET_SETTINGS_KEY = RegistryKey.ofRegistry<PlanetSettings>(id("planet_settings"))!!
        val PLANETS_KEY = RegistryKey.ofRegistry<Planet>(id("planets"))!!
        val STAR_SYSTEMS_KEY = RegistryKey.ofRegistry<StarSystem>(id("star_systems"))!!
        val GASES_KEY = RegistryKey.ofRegistry<Gas>(id("gases"))!!
    }

    @JvmField
    val PLANET_SETTINGS =
        SimpleDefaultedRegistry(id("planet_settings").toString(), Keys.PLANET_SETTINGS_KEY, Lifecycle.experimental(), false)

    @JvmField
    val PLANETS = SimpleDefaultedRegistry(id("planet").toString(), Keys.PLANETS_KEY, Lifecycle.experimental(), false)

    @JvmField
    val STAR_SYSTEMS =
        SimpleDefaultedRegistry(id("star_system").toString(), Keys.STAR_SYSTEMS_KEY, Lifecycle.experimental(), false)

    @JvmField
    val GASES = SimpleDefaultedRegistry(id("gas").toString(), Keys.GASES_KEY, Lifecycle.experimental(), false)
}
