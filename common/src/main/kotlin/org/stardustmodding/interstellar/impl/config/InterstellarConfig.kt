package org.stardustmodding.interstellar.impl.config

import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry
import me.shedaniel.autoconfig.serializer.PartitioningSerializer

@Config(name = "interstellar")
class InterstellarConfig : PartitioningSerializer.GlobalData() {
    @ConfigEntry.Category("core")
    @ConfigEntry.Gui.TransitiveObject
    var core = CoreConfig()
}
