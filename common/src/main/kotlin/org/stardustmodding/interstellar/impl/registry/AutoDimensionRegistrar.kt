package org.stardustmodding.interstellar.impl.registry

import net.minecraft.server.MinecraftServer
import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry
import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.impl.Interstellar.LOGGER

object AutoDimensionRegistrar {
    fun autoLoad(server: MinecraftServer) {
        val reg = DynamicDimensionRegistry.from(server)

        for (entry in InterstellarRegistries.PLANETS.entrySet) {
            val id = entry.key.value
            val planet = entry.value

            LOGGER.info("Creating/loading dynamic dimension $id...")

            planet.create(id, reg)
        }

        for (dim in reg.getDynamicDimensions()) {
            if (!InterstellarRegistries.PLANETS.containsId(dim.value)) {
                LOGGER.info("Removing unused dimension ${dim.value}...")

                reg.removeDynamicDimension(dim.value) { srv, player ->
                    player?.teleport(srv?.overworld, player.x, player.y, player.z, player.yaw, player.pitch)
                }
            }
        }
    }
}