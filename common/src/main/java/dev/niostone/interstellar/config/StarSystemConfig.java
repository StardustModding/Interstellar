package dev.niostone.interstellar.config;

import java.util.List;
import java.util.ArrayList;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "system")
public class StarSystemConfig implements ConfigData {
    public int id;
    public String name;
    public List<Integer> planets;

    public StarSystemConfig(String name) {
        this.name = name;
        this.planets = new ArrayList<>();
    }
}
