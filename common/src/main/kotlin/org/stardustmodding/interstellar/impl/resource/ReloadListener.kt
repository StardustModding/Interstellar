package org.stardustmodding.interstellar.impl.resource

import kotlinx.serialization.json.Json
import net.minecraft.registry.Registry
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.SynchronousResourceReloader
import org.stardustmodding.interstellar.api.registry.RegistryUtil
import org.stardustmodding.interstellar.api.planet.Planet
import org.stardustmodding.interstellar.api.planet.PlanetSettings
import org.stardustmodding.interstellar.api.registry.InterstellarRegistries
import org.stardustmodding.interstellar.api.starsystem.StarSystem
import org.stardustmodding.interstellar.impl.Interstellar.LOGGER

object ReloadListener: SynchronousResourceReloader {
    override fun reload(manager: ResourceManager) {
        reloadPlanetSettings(manager)
        reloadPlanets(manager)
        reloadStarSystems(manager)
    }

    private fun reloadPlanetSettings(manager: ResourceManager) {
        for (it in manager.findResources("planet_settings") { it.path.endsWith(".json") }) {
            val id = it.key.withPath(it.key.path.replace(".json", "").removePrefix("planet_settings/"))
            val stream = it.value.reader
            val raw = stream.readText()
            val data = Json.decodeFromString<PlanetSettings>(raw)

            if (InterstellarRegistries.PLANET_SETTINGS.containsId(id)) {
                LOGGER.info("Re-registering planet settings ${id}...")

                RegistryUtil.unregister(InterstellarRegistries.PLANET_SETTINGS, id)
            } else {
                LOGGER.info("Registering planet settings ${id}...")
            }

            Registry.register(InterstellarRegistries.PLANET_SETTINGS, id, data)
            stream.close()
        }
    }

    private fun reloadPlanets(manager: ResourceManager) {
        for (it in manager.findResources("planets") { it.path.endsWith(".json") }) {
            val id = it.key.withPath(it.key.path.replace(".json", "").removePrefix("planets/"))
            val stream = it.value.reader
            val raw = stream.readText()
            val data = Json.decodeFromString<Planet>(raw)

            if (InterstellarRegistries.PLANETS.containsId(id)) {
                LOGGER.info("Re-registering planet ${id}...")

                RegistryUtil.unregister(InterstellarRegistries.PLANETS, id)
            } else {
                LOGGER.info("Registering planet ${id}...")
            }

            Registry.register(InterstellarRegistries.PLANETS, id, data)
            stream.close()
        }
    }

    private fun reloadStarSystems(manager: ResourceManager) {
        for (it in manager.findResources("star_systems") { it.path.endsWith(".json") }) {
            val id = it.key.withPath(it.key.path.replace(".json", "").removePrefix("star_systems/"))
            val stream = it.value.reader
            val raw = stream.readText()
            val data = Json.decodeFromString<StarSystem>(raw)

            if (InterstellarRegistries.STAR_SYSTEMS.containsId(id)) {
                LOGGER.info("Re-registering star system ${id}...")

                RegistryUtil.unregister(InterstellarRegistries.STAR_SYSTEMS, id)
            } else {
                LOGGER.info("Registering star system ${id}...")
            }

            Registry.register(InterstellarRegistries.STAR_SYSTEMS, id, data)
            stream.close()
        }
    }
}
