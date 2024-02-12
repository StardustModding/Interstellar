package dev.niostone.interstellar.forge

import dev.architectury.platform.forge.EventBuses
import dev.niostone.interstellar.impl.Interstellar
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Interstellar.MOD_ID)
object InterstellarForge {
    init {
        EventBuses.registerModEventBus(Interstellar.MOD_ID, MOD_BUS)
        Interstellar.init()
    }
}
