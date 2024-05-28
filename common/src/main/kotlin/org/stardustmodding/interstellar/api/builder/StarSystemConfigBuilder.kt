package org.stardustmodding.interstellar.api.builder

import org.stardustmodding.interstellar.impl.config.StarSystemConfig
import net.minecraft.resources.ResourceLocation

class StarSystemConfigBuilder {
    val config: StarSystemConfig

    constructor(name: String) {
        this.config = StarSystemConfig(name);
    }

    constructor(name: ResourceLocation) {
        this.config = StarSystemConfig(name.toString());
    }

    constructor(namespace: String, id: String) {
        this.config = StarSystemConfig(namespace + ":" + id);
    }

    fun id(value: Int) = apply { this.config.id = value }
    fun planet(value: Int) = apply { this.config.planets.add(value) }

    fun build(): StarSystemConfig {
        return this.config
    }
}
