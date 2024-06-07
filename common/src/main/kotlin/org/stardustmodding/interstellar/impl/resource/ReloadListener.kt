package org.stardustmodding.interstellar.impl.resource

import kotlinx.serialization.json.Json
import net.minecraft.registry.Registry
import net.minecraft.resource.Resource
import net.minecraft.resource.ResourceManager
import net.minecraft.resource.SynchronousResourceReloader
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.gas.Gas
import org.stardustmodding.interstellar.api.planet.Planet
import org.stardustmodding.interstellar.api.planet.PlanetSettings
import org.stardustmodding.interstellar.api.registry.InterstellarRegistries
import org.stardustmodding.interstellar.api.registry.RegistryUtil
import org.stardustmodding.interstellar.api.starsystem.StarSystem
import org.stardustmodding.interstellar.impl.Interstellar.LOGGER

object ReloadListener : SynchronousResourceReloader {
    override fun reload(manager: ResourceManager) {
        reloadGases(manager)
        reloadPlanetSettings(manager)
        reloadPlanets(manager)
        reloadStarSystems(manager)
    }

    private fun findResources(manager: ResourceManager, prefix: String): List<Pair<Identifier, Resource>> {
        return manager.findResources(prefix) { it.path.endsWith(".json") }.map {
            Pair(
                it.key.withPath(
                    it.key.path.replace(".json", "").removePrefix(
                        "$prefix/"
                    )
                ), it.value
            )
        }
    }

    private fun reloadGases(manager: ResourceManager) {
        for ((id, res) in findResources(manager, "gases")) {
            val stream = res.reader
            val raw = stream.readText()
            val data = Json.decodeFromString<Gas>(raw)

            if (InterstellarRegistries.GASES.containsId(id)) {
                LOGGER.info("Re-registering gas ${id}...")

                RegistryUtil.unregister(InterstellarRegistries.GASES, id)
            } else {
                LOGGER.info("Registering gas ${id}...")
            }

            Registry.register(InterstellarRegistries.GASES, id, data)
            stream.close()
        }
    }

    private fun reloadPlanetSettings(manager: ResourceManager) {
        for ((id, res) in findResources(manager, "planet_settings")) {
            val stream = res.reader
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
        for ((id, res) in findResources(manager, "planets")) {
            val stream = res.reader
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
        for ((id, res) in findResources(manager, "star_systems")) {
            val stream = res.reader
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
