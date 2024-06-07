package org.stardustmodding.interstellar.api.event

import net.minecraft.server.MinecraftServer

interface Ticked {
    fun tick(server: MinecraftServer, delta: Float)
}
