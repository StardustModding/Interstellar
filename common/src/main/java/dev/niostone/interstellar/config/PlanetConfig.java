package dev.niostone.interstellar.config;

import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "planet")
public class PlanetConfig implements ConfigData {
    public boolean enable;
    public int dimensionId;
    public String name;
    public int pressure;
    public int radiation;
    public int tier;
    public boolean enableOxygen;
    public boolean enablePressure;
    public boolean enableRadiation;

    public List<GasConfig> gases;
}
