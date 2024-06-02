package org.stardustmodding.interstellar.forge.platform

import net.minecraft.registry.RegistryKey
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import org.jetbrains.annotations.ApiStatus
import org.stardustmodding.dynamicdimensions.api.event.DimensionAddedCallback
import org.stardustmodding.dynamicdimensions.api.event.DimensionRemovedCallback
import org.stardustmodding.dynamicdimensions.api.event.DynamicDimensionLoadCallback
import org.stardustmodding.dynamicdimensions.impl.config.DynamicDimensionsConfig
import org.stardustmodding.dynamicdimensions.impl.platform.services.PlatformHelper
import org.stardustmodding.interstellar.forge.config.DynamicDimensionsConfigImpl

@ApiStatus.Internal
class ForgePlatformHelper : PlatformHelper {
    private val addedCallbacks: MutableList<DimensionAddedCallback> = ArrayList<DimensionAddedCallback>()
    private val removedCallbacks: MutableList<DimensionRemovedCallback> = ArrayList<DimensionRemovedCallback>()
    private val loadCallbacks: MutableList<DynamicDimensionLoadCallback> =
        ArrayList() //todo: use forge event system?

    override val config: DynamicDimensionsConfig
        get() = DynamicDimensionsConfigImpl.INSTANCE

    override fun registerAddedEvent(listener: DimensionAddedCallback?) {
        addedCallbacks.add(listener!!)
    }

    override fun registerRemovedEvent(listener: DimensionRemovedCallback?) {
        removedCallbacks.add(listener!!)
    }

    override fun registerLoadEvent(callback: DynamicDimensionLoadCallback?) {
        loadCallbacks.add(callback!!)
    }

    override fun invokeRemovedEvent(key: RegistryKey<World?>, level: ServerWorld) {
        for (removedCallback in this.removedCallbacks) {
            removedCallback.dimensionRemoved(key, level)
        }
    }

    override fun invokeAddedEvent(key: RegistryKey<World?>, level: ServerWorld) {
        for (addedCallback in this.addedCallbacks) {
            addedCallback.dimensionAdded(key, level)
        }
    }

    override fun invokeLoadEvent(server: MinecraftServer, loader: DynamicDimensionLoadCallback.DynamicDimensionLoader) {
        for (loadCallback in this.loadCallbacks) {
            loadCallback.loadDimensions(server, loader)
        }
    }
}
