package dev.niostone.interstellar.config.builder;

import dev.niostone.interstellar.config.StarSystemConfig;
import net.minecraft.resources.ResourceLocation;

public class StarSystemConfigBuilder {
    private StarSystemConfig config;

    public StarSystemConfigBuilder(String name) {
        this.config = new StarSystemConfig(name);
    }

    public StarSystemConfigBuilder(ResourceLocation name) {
        this.config = new StarSystemConfig(name.toString());
    }

    public StarSystemConfigBuilder(String namespace, String id) {
        this.config = new StarSystemConfig(namespace + ":" + id);
    }

    public StarSystemConfigBuilder id(int id) {
        this.config.id = id;

        return this;
    }

    public StarSystemConfigBuilder planet(int planetId) {
        this.config.planets.add(planetId);

        return this;
    }

    public StarSystemConfig build() {
        return this.config;
    }
}
