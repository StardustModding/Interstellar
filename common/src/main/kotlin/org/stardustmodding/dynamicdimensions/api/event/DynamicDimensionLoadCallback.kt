package org.stardustmodding.dynamicdimensions.api.event

import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.chunk.ChunkGenerator
import org.stardustmodding.dynamicdimensions.impl.platform.Services

/**
 * Called when a dynamic dimension is added.
 *
 * @since 0.5.0
 */
fun interface DynamicDimensionLoadCallback {
    fun loadDimensions(server: MinecraftServer, loader: DynamicDimensionLoader)

    fun interface DynamicDimensionLoader {
        fun loadDynamicDimension(id: Identifier, chunkGenerator: ChunkGenerator, type: DimensionType)
    }

    companion object {
        fun register(callback: DynamicDimensionLoadCallback?) {
            Services.PLATFORM.registerLoadEvent(callback)
        }

        @JvmStatic
        fun invoke(server: MinecraftServer, loader: DynamicDimensionLoader) {
            Services.PLATFORM.invokeLoadEvent(server, loader)
        }
    }
}