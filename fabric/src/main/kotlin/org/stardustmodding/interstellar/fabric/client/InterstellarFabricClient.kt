package org.stardustmodding.interstellar.fabric.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import org.stardustmodding.dynamicdimensions.fabric.client.DynamicDimensionsFabricClient
import org.stardustmodding.interstellar.impl.InterstellarClient

@Environment(EnvType.CLIENT)
object InterstellarFabricClient : ClientModInitializer {
    private val dyndims = DynamicDimensionsFabricClient()

    override fun onInitializeClient() {
        dyndims.onInitializeClient()
        InterstellarClient.init()
    }
}
