package org.stardustmodding.dynamicdimensions.api

import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity

/**
 * Removes players from a [net.minecraft.world.World].
 */
fun interface PlayerRemover {
    /**
     * Called when a player must be removed from the level.
     * May cause unexpected behaviour if the player is not actually removed from the level.
     *
     * @param server The server instance
     * @param player The player to be removed
     */
    fun removePlayer(server: MinecraftServer?, player: ServerPlayerEntity?)
}
