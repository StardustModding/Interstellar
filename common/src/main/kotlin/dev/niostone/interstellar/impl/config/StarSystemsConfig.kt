package dev.niostone.interstellar.impl.config

import dev.niostone.interstellar.impl.config.defaults.DefaultStarSystemConfigs
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "systems")
class StarSystemsConfig: ConfigData {
    @ConfigEntry.Category("sol")
    @ConfigEntry.Gui.CollapsibleObject
    val sol = DefaultStarSystemConfigs.SOL

//    @ConfigEntry.Category("keplerBelt")
//    @ConfigEntry.Gui.CollapsibleObject
//    val keplerBelt
//
//    @ConfigEntry.Category("proximaCentauri")
//    @ConfigEntry.Gui.CollapsibleObject
//    val proximaCentauri
//
//    @ConfigEntry.Category("tauCeti")
//    @ConfigEntry.Gui.CollapsibleObject
//    val tauCeti
//
//    @ConfigEntry.Category("gliese892")
//    @ConfigEntry.Gui.CollapsibleObject
//    val gliese892
}
