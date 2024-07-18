package org.stardustmodding.interstellar.api.registry

import com.mojang.serialization.Lifecycle
import net.minecraft.core.DefaultedMappedRegistry
import net.minecraft.resources.ResourceKey
import org.stardustmodding.interstellar.api.gas.Gas
import org.stardustmodding.interstellar.api.planet.Planet
import org.stardustmodding.interstellar.api.planet.PlanetSettings
import org.stardustmodding.interstellar.api.starsystem.StarSystem
import org.stardustmodding.interstellar.impl.Interstellar.id

object InterstellarRegistries {
    object Keys {
        val PLANET_SETTINGS_KEY = ResourceKey.createRegistryKey<PlanetSettings>(id("planet_settings"))
        val PLANETS_KEY = ResourceKey.createRegistryKey<Planet>(id("planets"))
        val STAR_SYSTEMS_KEY = ResourceKey.createRegistryKey<StarSystem>(id("star_systems"))
        val GASES_KEY = ResourceKey.createRegistryKey<Gas>(id("gases"))
    }

    @JvmField
    val PLANET_SETTINGS =
        DefaultedMappedRegistry(id("planet_settings").toString(), Keys.PLANET_SETTINGS_KEY, Lifecycle.experimental(), false)

    @JvmField
    val PLANETS = DefaultedMappedRegistry(id("planet").toString(), Keys.PLANETS_KEY, Lifecycle.experimental(), false)

    @JvmField
    val STAR_SYSTEMS =
        DefaultedMappedRegistry(id("star_system").toString(), Keys.STAR_SYSTEMS_KEY, Lifecycle.experimental(), false)

    @JvmField
    val GASES = DefaultedMappedRegistry(id("gas").toString(), Keys.GASES_KEY, Lifecycle.experimental(), false)
}
