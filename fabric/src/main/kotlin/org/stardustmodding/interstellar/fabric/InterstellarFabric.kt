package org.stardustmodding.interstellar.fabric

import org.stardustmodding.interstellar.impl.Interstellar
import net.fabricmc.api.ModInitializer

object InterstellarFabric: ModInitializer {
    val dyndims = DynamicDimensionsFabric()

    override fun onInitialize() {
        dyndims.onInitialize()
        Interstellar.init()
    }
}