package org.stardustmodding.interstellar.fabric.client

import net.fabricmc.api.ClientModInitializer
import org.stardustmodding.dynamicdimensions.fabric.client.DynamicDimensionsFabricClient

object InterstellarFabricClient: ClientModInitializer {
    private val dyndims = DynamicDimensionsFabricClient()

    override fun onInitializeClient() {
        dyndims.onInitializeClient()
    }
}
