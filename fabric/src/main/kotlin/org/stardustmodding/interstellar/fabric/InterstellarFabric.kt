package org.stardustmodding.interstellar.fabric

import org.stardustmodding.interstellar.impl.Interstellar
import net.fabricmc.api.ModInitializer

object InterstellarFabric: ModInitializer {
    override fun onInitialize() {
        Interstellar.init()
    }
}