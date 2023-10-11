package dev.niostone.interstellar.config;

import java.util.ArrayList;
import java.util.List;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "planet")
public class PlanetConfig implements ConfigData {
    public boolean enable;
    public int dimensionId;
    public String name;
    // This is in kPa
    public float pressure;
    // In μSv/tick
    public float radiation;
    public int tier;
    public boolean enableOxygen;
    public boolean enablePressure;
    public boolean enableRadiation;
    public List<GasConfig> gases;

    public PlanetConfig(String name) {
        this.name = name;
        this.gases = new ArrayList<>();
    }
}
