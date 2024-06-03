package org.stardustmodding.interstellar.fabric

import org.stardustmodding.interstellar.impl.Interstellar
import net.fabricmc.api.ModInitializer
import org.stardustmodding.dynamicdimensions.fabric.DynamicDimensionsFabric

object InterstellarFabric : ModInitializer {
    private val dyndims = DynamicDimensionsFabric()

    override fun onInitialize() {
        dyndims.onInitialize()
        Interstellar.init()
    }
}