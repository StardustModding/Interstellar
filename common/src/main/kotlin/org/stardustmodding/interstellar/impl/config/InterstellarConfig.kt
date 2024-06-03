package org.stardustmodding.interstellar.impl.config

import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import me.shedaniel.autoconfig.serializer.PartitioningSerializer

@Config(name = "interstellar")
class InterstellarConfig : PartitioningSerializer.GlobalData() {
    @ConfigEntry.Category("planets")
    @ConfigEntry.Gui.TransitiveObject
    val planets = PlanetsConfig()

    @ConfigEntry.Category("systems")
    @ConfigEntry.Gui.TransitiveObject
    val starSystems = StarSystemsConfig()

    @ConfigEntry.Category("core")
    @ConfigEntry.Gui.TransitiveObject
    val dynamicDimensions = CoreConfig()
}
