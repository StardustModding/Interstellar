package dev.niostone.interstellar.impl.init

import dev.niostone.interstellar.impl.Interstellar
import dev.niostone.interstellar.impl.world.biome.MoonPlains
import dev.niostone.interstellar.impl.util.RegistryLookup
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome

object Biomes {
    var MOON_PLAINS: Biome? = null

    fun register() {
        MOON_PLAINS = Registry.register(
                RegistryLookup.BIOMES,
                ResourceLocation(Interstellar.MOD_ID),
            MoonPlains.BIOME
        )
    }
}
