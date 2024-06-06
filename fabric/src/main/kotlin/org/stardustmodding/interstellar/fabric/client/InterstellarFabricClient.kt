package org.stardustmodding.interstellar.fabric.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import org.stardustmodding.interstellar.impl.client.InterstellarClient

@Environment(EnvType.CLIENT)
object InterstellarFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        InterstellarClient.init()
    }
}
