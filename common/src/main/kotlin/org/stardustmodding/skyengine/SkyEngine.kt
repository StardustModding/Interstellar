package org.stardustmodding.skyengine

import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier

object SkyEngine {
    fun id(value: String) = Identifier("skyengine", value)

    fun init() {

    }

    fun tick(server: MinecraftServer) {}
}
