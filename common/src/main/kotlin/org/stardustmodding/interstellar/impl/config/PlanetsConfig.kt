package org.stardustmodding.interstellar.impl.config

import org.stardustmodding.interstellar.impl.config.defaults.DefaultPlanetConfigs
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "planets")
class PlanetsConfig: ConfigData {
    @ConfigEntry.Category("mercury")
    @ConfigEntry.Gui.CollapsibleObject
    val mercury = DefaultPlanetConfigs.MERCURY

    @ConfigEntry.Category("venus")
    @ConfigEntry.Gui.CollapsibleObject
    val venus = DefaultPlanetConfigs.VENUS

    @ConfigEntry.Category("earth")
    @ConfigEntry.Gui.CollapsibleObject
    val earth = DefaultPlanetConfigs.EARTH

    @ConfigEntry.Category("moon")
    @ConfigEntry.Gui.CollapsibleObject
    val moon = DefaultPlanetConfigs.MOON

    @ConfigEntry.Category("mars")
    @ConfigEntry.Gui.CollapsibleObject
    val mars = DefaultPlanetConfigs.MARS

    // Just realized that this is a gas giant, don't know how we're
    // going to handle this.
    // @ConfigEntry.Category("jupiter")
    // @ConfigEntry.Gui.CollapsibleObject
    // val jupiter = DefaultPlanetConfigs.JUPITER

    @ConfigEntry.Category("saturn")
    @ConfigEntry.Gui.CollapsibleObject
    val saturn = DefaultPlanetConfigs.SATURN

    @ConfigEntry.Category("uranus")
    @ConfigEntry.Gui.CollapsibleObject
    val uranus = DefaultPlanetConfigs.URANUS

    @ConfigEntry.Category("neptune")
    @ConfigEntry.Gui.CollapsibleObject
    val neptune = DefaultPlanetConfigs.NEPTUNE

    @ConfigEntry.Category("pluto")
    @ConfigEntry.Gui.CollapsibleObject
    val pluto = DefaultPlanetConfigs.PLUTO

//    @ConfigEntry.Category("keplerAsteroids")
//    @ConfigEntry.Gui.CollapsibleObject
//    val keplerAsteroids
//
//    @ConfigEntry.Category("proximaCentauriD")
//    @ConfigEntry.Gui.CollapsibleObject
//    val proximaCentauriD
//
//    @ConfigEntry.Category("proximaCentauriB")
//    @ConfigEntry.Gui.CollapsibleObject
//    val proximaCentauriB
//
//    @ConfigEntry.Category("proximaCentauriC")
//    @ConfigEntry.Gui.CollapsibleObject
//    val proximaCentauriC
//
//    @ConfigEntry.Category("tauCetiB")
//    @ConfigEntry.Gui.CollapsibleObject
//    val tauCetiB
//
//    @ConfigEntry.Category("tauCetiG")
//    @ConfigEntry.Gui.CollapsibleObject
//    val tauCetiG
//
//    @ConfigEntry.Category("tauCetiC")
//    @ConfigEntry.Gui.CollapsibleObject
//    val tauCetiC
//
//    @ConfigEntry.Category("tauCetiH")
//    @ConfigEntry.Gui.CollapsibleObject
//    val tauCetiH
//
//    @ConfigEntry.Category("tauCetiD")
//    @ConfigEntry.Gui.CollapsibleObject
//    val tauCetiD
//
//    @ConfigEntry.Category("tauCetiE")
//    @ConfigEntry.Gui.CollapsibleObject
//    val tauCetiE
//
//    @ConfigEntry.Category("tauCetiF")
//    @ConfigEntry.Gui.CollapsibleObject
//    val tauCetiF
//
//    @ConfigEntry.Category("tauCetiI")
//    @ConfigEntry.Gui.CollapsibleObject
//    val tauCetiI
//
//    @ConfigEntry.Category("gliese892B")
//    @ConfigEntry.Gui.CollapsibleObject
//    val gliese892B
//
//    @ConfigEntry.Category("gliese892C")
//    @ConfigEntry.Gui.CollapsibleObject
//    val gliese892C
//
//    @ConfigEntry.Category("gliese892F")
//    @ConfigEntry.Gui.CollapsibleObject
//    val gliese892F
//
//    @ConfigEntry.Category("gliese892D")
//    @ConfigEntry.Gui.CollapsibleObject
//    val gliese892D
//
//    @ConfigEntry.Category("gliese892G")
//    @ConfigEntry.Gui.CollapsibleObject
//    val gliese892G
//
//    @ConfigEntry.Category("gliese892H")
//    @ConfigEntry.Gui.CollapsibleObject
//    val gliese892H
}
