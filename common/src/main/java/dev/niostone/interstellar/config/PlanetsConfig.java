package dev.niostone.interstellar.config;

import dev.niostone.interstellar.config.defaults.DefaultPlanetConfigs;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "planets")
public class PlanetsConfig implements ConfigData {
    @ConfigEntry.Category("mercury")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig mercury = DefaultPlanetConfigs.MERCURY;

    @ConfigEntry.Category("venus")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig venus = DefaultPlanetConfigs.VENUS;

    @ConfigEntry.Category("earth")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig earth = DefaultPlanetConfigs.EARTH;

    @ConfigEntry.Category("moon")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig moon = DefaultPlanetConfigs.MOON;

    @ConfigEntry.Category("mars")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig mars = DefaultPlanetConfigs.MARS;

    @ConfigEntry.Category("jupiter")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig jupiter;

    @ConfigEntry.Category("saturn")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig saturn;

    @ConfigEntry.Category("uranus")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig uranus;

    @ConfigEntry.Category("neptune")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig neptune;

    @ConfigEntry.Category("pluto")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig pluto;

    @ConfigEntry.Category("keplerAsteroids")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig keplerAsteroids;

    @ConfigEntry.Category("proximaCentauriD")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig proximaCentauriD;

    @ConfigEntry.Category("proximaCentauriB")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig proximaCentauriB;

    @ConfigEntry.Category("proximaCentauriC")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig proximaCentauriC;

    @ConfigEntry.Category("tauCetiB")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig tauCetiB;

    @ConfigEntry.Category("tauCetiG")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig tauCetiG;

    @ConfigEntry.Category("tauCetiC")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig tauCetiC;

    @ConfigEntry.Category("tauCetiH")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig tauCetiH;

    @ConfigEntry.Category("tauCetiD")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig tauCetiD;

    @ConfigEntry.Category("tauCetiE")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig tauCetiE;

    @ConfigEntry.Category("tauCetiF")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig tauCetiF;

    @ConfigEntry.Category("tauCetiI")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig tauCetiI;

    @ConfigEntry.Category("gliese892B")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig gliese892B;

    @ConfigEntry.Category("gliese892C")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig gliese892C;

    @ConfigEntry.Category("gliese892F")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig gliese892F;

    @ConfigEntry.Category("gliese892D")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig gliese892D;

    @ConfigEntry.Category("gliese892G")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig gliese892G;

    @ConfigEntry.Category("gliese892H")
    @ConfigEntry.Gui.TransitiveObject
    public PlanetConfig gliese892H;
}
