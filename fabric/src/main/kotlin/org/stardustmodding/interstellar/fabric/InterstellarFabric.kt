package org.stardustmodding.interstellar.fabric

import dev.galacticraft.dynamicdimensions.impl.fabric.DynamicDimensionsFabric
import org.stardustmodding.interstellar.impl.Interstellar
import net.fabricmc.api.ModInitializer

object InterstellarFabric: ModInitializer {
    val dyndims = DynamicDimensionsFabric()

    override fun onInitialize() {
        dyndims.onInitialize()
        Interstellar.init()
    }
}