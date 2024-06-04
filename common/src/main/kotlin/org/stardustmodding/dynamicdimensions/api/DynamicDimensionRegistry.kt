package org.stardustmodding.dynamicdimensions.api

import net.minecraft.registry.RegistryKey
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.world.World
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.chunk.ChunkGenerator
import org.jetbrains.annotations.Contract

/**
 * The registry for dynamic dimensions.
 * It is not possible to access the registry from the client.
 *
 * @since 0.1.0
 * @see .from
 */
interface DynamicDimensionRegistry {
    fun getDynamicDimensions(): List<RegistryKey<World>>

    /**
     * Returns whether a dynamic dimension exists with the given id
     *
     * @param id the id of the dynamic dimension
     * @return whether a dynamic dimension exists with the given id
     */
    fun dynamicDimensionExists(id: Identifier): Boolean

    /**
     * Returns whether any dimension, dimension type, or level stem is registered with the given id
     *
     * @param id the id of the dimension
     * @return whether any dimension, dimension type, or level stem is registered with the given id
     */
    fun anyDimensionExists(id: Identifier): Boolean

    /**
     * Returns whether a level and dimension with the given ID can be deleted.
     *
     * @param id The ID of the level/dimension.
     * @return `true` if the level and dimension are dynamic and can be deleted, `false` otherwise.
     * @since 0.1.0
     */
    fun canDeleteDimension(id: Identifier): Boolean

    /**
     * Returns whether a level and dimension with the given ID can be created.
     *
     * @param id The ID of the level/dimension.
     * @return `true` if the level and dimension are dynamic and can be created, `false` otherwise.
     * @since 0.6.0
     */
    fun canCreateDimension(id: Identifier): Boolean

    /**
     * Registers a new dimension and updates all clients with the new dimension.
     * If world data already exists for this dimension it may be deleted
     * NOTE: The dimension may not be loaded until the next tick.
     *
     * @param chunkGenerator The chunk generator.
     * @param id             The ID of the dimension.
     * This ID must be unique and unused in the [net.minecraft.registry.RegistryKeys.DIMENSION_TYPE] registry and the [net.minecraft.world.dimension.DimensionOptionsRegistryHolder.dimensions] registry.
     * @param type           The dimension type.
     * @return whether a dimension with the given id was created
     * @since 0.6.0
     */
    fun createDynamicDimension(id: Identifier, chunkGenerator: ChunkGenerator, type: DimensionType): Boolean

    /**
     * Registers a new dimension and updates all clients with the new dimension.
     * If world data already exists for this dimension it will be used, otherwise it will be generated
     * NOTE: The dimension will not be loaded until the next tick.
     *
     * @param chunkGenerator The chunk generator.
     * @param id             The ID of the dimension.
     * This ID must be unique and unused in the [net.minecraft.registry.RegistryKeys.DIMENSION_TYPE] registry and the [net.minecraft.world.dimension.DimensionOptionsRegistryHolder.dimensions] registry.
     * @param type           The dimension type.
     * @return whether a dimension with the given id was created
     * @since 0.6.0
     */
    fun loadDynamicDimension(id: Identifier, chunkGenerator: ChunkGenerator, type: DimensionType): Boolean

    /**
     * Removes a dynamic dimension from the server.
     * This may delete the dimension files permanently.
     * Players will be removed from the dimension using the provided player remover.
     *
     * @param id      The ID of the dimension.
     * @param remover The method to remove players from the dimension.
     * @return whether a dimension with the given id was removed
     * @since 0.1.0
     */
    fun removeDynamicDimension(id: Identifier, remover: PlayerRemover): Boolean

    companion object {
        /**
         * Converts a Minecraft server instance into a dynamic dimension registry.
         *
         * @param server the current Minecraft server instance.
         * @return the server's dynamic dimension registry.
         * @since 0.5.0
         */
        @JvmStatic
        @Contract(value = "_ -> param1", pure = true)
        fun from(server: MinecraftServer): DynamicDimensionRegistry {
            return (server as DynamicDimensionRegistry)
        }
    }
}
