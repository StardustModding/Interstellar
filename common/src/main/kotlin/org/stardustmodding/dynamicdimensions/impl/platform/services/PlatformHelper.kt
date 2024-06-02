package org.stardustmodding.dynamicdimensions.impl.platform.services

import net.minecraft.registry.RegistryKey
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World
import org.stardustmodding.dynamicdimensions.api.event.DimensionAddedCallback
import org.stardustmodding.dynamicdimensions.api.event.DimensionRemovedCallback
import org.stardustmodding.dynamicdimensions.api.event.DynamicDimensionLoadCallback
import org.stardustmodding.dynamicdimensions.api.event.DynamicDimensionLoadCallback.DynamicDimensionLoader
import org.stardustmodding.dynamicdimensions.impl.config.DynamicDimensionsConfig

interface PlatformHelper {
    val config: DynamicDimensionsConfig

    fun registerAddedEvent(listener: DimensionAddedCallback?)
    fun registerRemovedEvent(listener: DimensionRemovedCallback?)
    fun registerLoadEvent(callback: DynamicDimensionLoadCallback?)
    fun invokeRemovedEvent(key: RegistryKey<World?>, level: ServerWorld)
    fun invokeAddedEvent(key: RegistryKey<World?>, level: ServerWorld)
    fun invokeLoadEvent(server: MinecraftServer, loader: DynamicDimensionLoader)
}
