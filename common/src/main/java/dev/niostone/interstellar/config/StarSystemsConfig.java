package dev.niostone.interstellar.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "systems")
public class StarSystemsConfig implements ConfigData {
    @ConfigEntry.Category("sol")
    @ConfigEntry.Gui.TransitiveObject
    public StarSystemConfig sol;

    @ConfigEntry.Category("keplerBelt")
    @ConfigEntry.Gui.TransitiveObject
    public StarSystemConfig keplerBelt;

    @ConfigEntry.Category("proximaCentauri")
    @ConfigEntry.Gui.TransitiveObject
    public StarSystemConfig proximaCentauri;

    @ConfigEntry.Category("tauCeti")
    @ConfigEntry.Gui.TransitiveObject
    public StarSystemConfig tauCeti;

    @ConfigEntry.Category("gliese892")
    @ConfigEntry.Gui.TransitiveObject
    public StarSystemConfig gliese892;
}
