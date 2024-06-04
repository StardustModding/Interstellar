package org.stardustmodding.interstellar.impl.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import org.stardustmodding.interstellar.impl.config.defaults.DefaultPlanetConfigs

@Config(name = "planets")
class PlanetsConfig: ConfigData {
    @ConfigEntry.Category("planets")
    @ConfigEntry.Gui.CollapsibleObject
    var planets = mutableListOf(
        DefaultPlanetConfigs.MERCURY,
        DefaultPlanetConfigs.VENUS,
        DefaultPlanetConfigs.EARTH,
        DefaultPlanetConfigs.MOON,
        DefaultPlanetConfigs.MARS,
        // DefaultPlanetConfigs.JUPITER,
        DefaultPlanetConfigs.SATURN,
        DefaultPlanetConfigs.URANUS,
        DefaultPlanetConfigs.NEPTUNE,
        DefaultPlanetConfigs.PLUTO,
        // keplerAsteroids,
        // proximaCentauriD,
        // proximaCentauriB,
        // proximaCentauriC,
        // tauCetiB,
        // tauCetiG,
        // tauCetiC,
        // tauCetiH,
        // tauCetiD,
        // tauCetiE,
        // tauCetiF,
        // tauCetiI,
        // gliese892B,
        // gliese892C,
        // gliese892F,
        // gliese892D,
        // gliese892G,
        // gliese892H,
    )
}