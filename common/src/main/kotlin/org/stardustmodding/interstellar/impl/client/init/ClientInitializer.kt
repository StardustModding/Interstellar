package org.stardustmodding.interstellar.impl.client.init

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.Minecraft
import org.stardustmodding.interstellar.api.init.InitializedClient

@Environment(EnvType.CLIENT)
object ClientInitializer : InitializedClient {
    override fun init(it: Minecraft) {
        initChild(it, ClientEntities)
        initChild(it, ClientScreens)
    }
}