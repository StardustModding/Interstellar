package org.stardustmodding.interstellar.impl.client

import dev.architectury.event.events.client.ClientLifecycleEvent
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import org.stardustmodding.interstellar.impl.client.init.ClientInitializer

@Environment(EnvType.CLIENT)
object InterstellarClient {
    fun init() {
        ClientLifecycleEvent.CLIENT_SETUP.register(ClientInitializer::init)
    }
}