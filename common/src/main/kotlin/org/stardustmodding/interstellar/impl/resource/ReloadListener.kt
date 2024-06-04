package org.stardustmodding.interstellar.impl.resource

import kotlinx.serialization.json.Json
import net.minecraft.registry.Registry
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.SynchronousResourceReloader
import org.stardustmodding.dynamicdimensions.impl.registry.RegistryUtil
import org.stardustmodding.interstellar.api.planet.Planet
import org.stardustmodding.interstellar.api.planet.PlanetSettings
import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.api.starsystem.StarSystem
import org.stardustmodding.interstellar.impl.Interstellar.LOGGER

object ReloadListener: SynchronousResourceReloader {
    override fun reload(manager: ResourceManager) {
        reloadPlanetSettings(manager)
        reloadPlanets(manager)
        reloadStarSystems(manager)
    }

    private fun reloadPlanetSettings(manager: ResourceManager) {
        for (id in manager.findResources("planet_settings") { it.path.endsWith(".json") }) {
            val stream = id.value.reader
            val raw = stream.readText()
            val data = Json.decodeFromString<PlanetSettings>(raw)

            if (InterstellarRegistries.PLANET_SETTINGS.containsId(id.key)) {
                LOGGER.info("Re-registering planet settings ${id.key}...")

                RegistryUtil.unregister(InterstellarRegistries.PLANET_SETTINGS, id.key)
            } else {
                LOGGER.info("Registering planet settings ${id.key}...")
            }

            Registry.register(InterstellarRegistries.PLANET_SETTINGS, id.key, data)
            stream.close()
        }
    }

    private fun reloadPlanets(manager: ResourceManager) {
        for (id in manager.findResources("planets") { it.path.endsWith(".json") }) {
            val stream = id.value.reader
            val raw = stream.readText()
            val data = Json.decodeFromString<Planet>(raw)

            if (InterstellarRegistries.PLANETS.containsId(id.key)) {
                LOGGER.info("Re-registering planet ${id.key}...")

                RegistryUtil.unregister(InterstellarRegistries.PLANETS, id.key)
            } else {
                LOGGER.info("Registering planet ${id.key}...")
            }

            Registry.register(InterstellarRegistries.PLANETS, id.key, data)
            stream.close()
        }
    }

    private fun reloadStarSystems(manager: ResourceManager) {
        for (id in manager.findResources("star_systems") { it.path.endsWith(".json") }) {
            val stream = id.value.reader
            val raw = stream.readText()
            val data = Json.decodeFromString<StarSystem>(raw)

            if (InterstellarRegistries.STAR_SYSTEMS.containsId(id.key)) {
                LOGGER.info("Re-registering star system ${id.key}...")

                RegistryUtil.unregister(InterstellarRegistries.STAR_SYSTEMS, id.key)
            } else {
                LOGGER.info("Registering star system ${id.key}...")
            }

            Registry.register(InterstellarRegistries.STAR_SYSTEMS, id.key, data)
            stream.close()
        }
    }
}
