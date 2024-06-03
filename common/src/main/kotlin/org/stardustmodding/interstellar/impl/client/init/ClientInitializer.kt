package org.stardustmodding.interstellar.impl.client.init

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient

@Environment(EnvType.CLIENT)
object ClientInitializer {
    fun init(client: MinecraftClient) {
        ClientEntities.register()
    }
}