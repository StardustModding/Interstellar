package org.stardustmodding.dynamicdimensions.api.event

import net.minecraft.registry.RegistryKey
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import org.jetbrains.annotations.ApiStatus
import org.stardustmodding.dynamicdimensions.impl.platform.Services

/**
 * Called when a dynamic dimension is added.
 * @since 0.5.0
 */
fun interface DimensionAddedCallback {
    fun dimensionAdded(key: RegistryKey<World?>, level: ServerWorld)

    companion object {
        fun register(callback: DimensionAddedCallback?) {
            Services.PLATFORM.registerAddedEvent(callback)
        }

        @ApiStatus.Internal
        fun invoke(key: RegistryKey<World?>, level: ServerWorld) {
            Services.PLATFORM.invokeAddedEvent(key, level)
        }
    }
}
