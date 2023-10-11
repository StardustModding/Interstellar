package dev.niostone.interstellar.config.builder;

import dev.niostone.interstellar.config.GasConfig;
import dev.niostone.interstellar.config.PlanetConfig;
import net.minecraft.resources.ResourceLocation;

public class PlanetConfigBuilder {
    private PlanetConfig config;

    public PlanetConfigBuilder(String name) {
        this.config = new PlanetConfig(name);
    }

    public PlanetConfigBuilder(ResourceLocation name) {
        this.config = new PlanetConfig(name.toString());
    }

    public PlanetConfigBuilder(String namespace, String id) {
        this.config = new PlanetConfig(namespace + ":" + id);
    }

    public PlanetConfigBuilder enable(boolean inp) {
        this.config.enable = inp;
        return this;
    }

    public PlanetConfigBuilder dimension(int inp) {
        this.config.dimensionId = inp;
        return this;
    }

    public PlanetConfigBuilder withPressure(float inp) {
        this.config.pressure = inp;
        return this;
    }

    public PlanetConfigBuilder withRadiation(float inp) {
        this.config.radiation = inp;
        return this;
    }

    public PlanetConfigBuilder tier(int inp) {
        this.config.tier = inp;
        return this;
    }

    public PlanetConfigBuilder oxygen(boolean inp) {
        this.config.enableOxygen = inp;
        return this;
    }

    public PlanetConfigBuilder pressure(boolean inp) {
        this.config.enablePressure = inp;
        return this;
    }

    public PlanetConfigBuilder radiation(boolean inp) {
        this.config.enableRadiation = inp;
        return this;
    }

    public PlanetConfigBuilder gas(GasConfig inp) {
        this.config.gases.add(inp);
        return this;
    }

    public PlanetConfig build() {
        return this.config;
    }
}
