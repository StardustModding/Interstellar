package org.stardustmodding.skyengine

import net.minecraft.server.MinecraftServer
import net.minecraft.resources.ResourceLocation

object SkyEngine {
    fun id(value: String) = ResourceLocation("skyengine", value)

    fun init() {

    }

    fun tick(server: MinecraftServer) {}
}
