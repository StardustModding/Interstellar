package org.stardustmodding.interstellar.api.builder

import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.impl.config.StarSystemConfig

class StarSystemConfigBuilder {
    val config: StarSystemConfig

    constructor(name: String) {
        this.config = StarSystemConfig(name)
    }

    constructor(name: Identifier) {
        this.config = StarSystemConfig(name.toString())
    }

    constructor(namespace: String, id: String) {
        this.config = StarSystemConfig("$namespace:$id")
    }

    fun planet(value: Int) = apply { this.config.planets.add(value) }

    fun build(): StarSystemConfig {
        return this.config
    }
}
