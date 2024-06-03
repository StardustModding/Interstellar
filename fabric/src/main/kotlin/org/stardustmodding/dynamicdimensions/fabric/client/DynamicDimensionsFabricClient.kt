package org.stardustmodding.dynamicdimensions.fabric.client

import net.fabricmc.api.ClientModInitializer
import org.jetbrains.annotations.ApiStatus
import org.stardustmodding.dynamicdimensions.impl.client.network.DynamicDimensionsS2CPacketReceivers.registerReceivers

@ApiStatus.Internal
class DynamicDimensionsFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        registerReceivers()
    }
}
