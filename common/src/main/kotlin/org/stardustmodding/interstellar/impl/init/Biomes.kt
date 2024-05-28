package org.stardustmodding.interstellar.impl.init

import net.minecraft.core.Holder
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.util.RegistryLookup
import org.stardustmodding.interstellar.impl.world.biome.MoonPlains
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome

object Biomes {
    var MOON_PLAINS: Holder.Reference<Biome>? = null

    fun register() {
        MOON_PLAINS = Registry.registerForHolder(
            RegistryLookup.BIOMES!!,
            MoonPlains().getLocation(),
            MoonPlains().getBiome()
        )
    }
}
