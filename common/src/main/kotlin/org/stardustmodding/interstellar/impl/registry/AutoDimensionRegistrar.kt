package org.stardustmodding.interstellar.impl.registry

import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry
import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.impl.Interstellar.LOGGER

object AutoDimensionRegistrar {
    @JvmStatic
    fun autoRegister(reg: DynamicDimensionRegistry) {
        for (entry in InterstellarRegistries.PLANETS.entrySet) {
            val id = entry.key.value
            val planet = entry.value

            LOGGER.info("Creating/loading dynamic dimension $id...")

            planet.create(id, reg)
        }

        for (dim in reg.getDynamicDimensions()) {
            if (!InterstellarRegistries.PLANETS.containsId(dim.value)) {
                LOGGER.info("Removing unused dimension ${dim.value}...")

                reg.removeDynamicDimension(dim.value) { server, player ->
                    player?.teleport(server?.overworld, player.x, player.y, player.z, player.yaw, player.pitch)
                }
            }
        }

        LOGGER.info("Auto-registered all dynamic dimensions!")
    }
}