package org.stardustmodding.dynamicdimensions.fabric.platform

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents
import net.minecraft.registry.RegistryKey
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import org.jetbrains.annotations.ApiStatus
import org.stardustmodding.dynamicdimensions.api.event.DimensionAddedCallback
import org.stardustmodding.dynamicdimensions.api.event.DimensionRemovedCallback
import org.stardustmodding.dynamicdimensions.api.event.DynamicDimensionLoadCallback
import org.stardustmodding.dynamicdimensions.fabric.DynamicDimensionsFabric
import org.stardustmodding.dynamicdimensions.fabric.config.DynamicDimensionsConfigImpl.Companion.create
import org.stardustmodding.dynamicdimensions.impl.config.DynamicDimensionsConfig
import org.stardustmodding.dynamicdimensions.impl.platform.services.PlatformHelper

@ApiStatus.Internal
class FabricPlatformHelper : PlatformHelper {
    override val config: DynamicDimensionsConfig
        get() = create()

    override fun registerAddedEvent(listener: DimensionAddedCallback?) {
        DynamicDimensionsFabric.DIMENSION_ADDED_EVENT.register(listener)
    }

    override fun registerRemovedEvent(listener: DimensionRemovedCallback?) {
        DynamicDimensionsFabric.DIMENSION_REMOVED_EVENT.register(listener)
    }

    override fun registerLoadEvent(callback: DynamicDimensionLoadCallback?) {
        DynamicDimensionsFabric.DIMENSION_LOAD_EVENT.register(callback)
    }

    override fun invokeRemovedEvent(key: RegistryKey<World?>, level: ServerWorld) {
        DynamicDimensionsFabric.DIMENSION_REMOVED_EVENT.invoker()!!.dimensionRemoved(key, level)
        ServerWorldEvents.UNLOAD.invoker().onWorldUnload(level.server, level)
    }

    override fun invokeAddedEvent(key: RegistryKey<World?>, level: ServerWorld) {
        DynamicDimensionsFabric.DIMENSION_ADDED_EVENT.invoker()!!.dimensionAdded(key, level)
        ServerWorldEvents.LOAD.invoker().onWorldLoad(level.server, level)
    }

    override fun invokeLoadEvent(server: MinecraftServer, loader: DynamicDimensionLoadCallback.DynamicDimensionLoader) {
        DynamicDimensionsFabric.DIMENSION_LOAD_EVENT.invoker()!!.loadDimensions(server, loader)
    }
}
