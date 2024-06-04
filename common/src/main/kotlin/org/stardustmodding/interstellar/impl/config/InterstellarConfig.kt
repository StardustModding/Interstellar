package org.stardustmodding.interstellar.impl.config

import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import me.shedaniel.autoconfig.serializer.PartitioningSerializer

@Config(name = "interstellar")
class InterstellarConfig : PartitioningSerializer.GlobalData() {
    @ConfigEntry.Category("planets")
    @ConfigEntry.Gui.TransitiveObject
    var planets = PlanetsConfig()

    @ConfigEntry.Category("systems")
    @ConfigEntry.Gui.TransitiveObject
    var starSystems = StarSystemsConfig()

    @ConfigEntry.Category("core")
    @ConfigEntry.Gui.TransitiveObject
    var dynamicDimensions = CoreConfig()
}
