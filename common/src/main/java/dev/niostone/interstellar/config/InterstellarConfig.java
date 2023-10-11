package dev.niostone.interstellar.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "interstellar")
public class InterstellarConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Category("planets")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetsConfig planets;

    @ConfigEntry.Category("systems")
    @ConfigEntry.Gui.TransitiveObject
    public StarSystemsConfig starSystems;
}
