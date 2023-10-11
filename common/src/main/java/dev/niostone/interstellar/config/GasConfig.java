package dev.niostone.interstellar.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "gas")
public class GasConfig implements ConfigData {
    public String id;
    public float quantity;
}
