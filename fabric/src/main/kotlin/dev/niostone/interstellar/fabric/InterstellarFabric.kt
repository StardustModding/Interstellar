package dev.niostone.interstellar.fabric

import dev.niostone.interstellar.impl.Interstellar
import net.fabricmc.api.ModInitializer

object InterstellarFabric: ModInitializer {
    override fun onInitialize() {
        Interstellar.init()
    }
}