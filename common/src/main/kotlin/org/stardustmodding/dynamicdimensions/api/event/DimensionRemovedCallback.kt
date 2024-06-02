package org.stardustmodding.dynamicdimensions.api.event

import net.minecraft.registry.RegistryKey
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import org.jetbrains.annotations.ApiStatus
import org.stardustmodding.dynamicdimensions.impl.platform.Services

/**
 * Called when a dynamic dimension is removed.
 * @since 0.5.0
 */
fun interface DimensionRemovedCallback {
    fun dimensionRemoved(key: RegistryKey<World?>, level: ServerWorld)

    companion object {
        fun register(callback: DimensionRemovedCallback?) {
            Services.PLATFORM.registerRemovedEvent(callback)
        }

        @ApiStatus.Internal
        fun invoke(key: RegistryKey<World?>, level: ServerWorld) {
            Services.PLATFORM.invokeRemovedEvent(key, level)
        }
    }
}