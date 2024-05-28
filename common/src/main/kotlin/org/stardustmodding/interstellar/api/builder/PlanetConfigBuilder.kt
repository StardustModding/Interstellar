package org.stardustmodding.interstellar.api.builder

import  org.stardustmodding.interstellar.impl.config.GasConfig
import org.stardustmodding.interstellar.impl.config.PlanetConfig
import net.minecraft.resources.ResourceLocation

class PlanetConfigBuilder {
    val config: PlanetConfig

    constructor(name: String) {
        this.config = PlanetConfig(name)
    }

    constructor(name: ResourceLocation) {
        this.config = PlanetConfig(name.toString())
    }

    constructor(namespace: String, id: String) {
        this.config = PlanetConfig("$namespace:$id")
    }

    fun enable(value: Boolean) = apply { this.config.enable = value }
    fun dimension(value: Int) = apply { this.config.dimensionId = value }
    fun enablePressure(value: Boolean) = apply { this.config.enablePressure = value }
    fun enableRadiation(value: Boolean) = apply { this.config.enableRadiation = value }
    fun tier(value: Int) = apply { this.config.tier = value }
    fun enableOxygen(value: Boolean) = apply { this.config.enableOxygen = value }
    fun pressure(value: Float) = apply { this.config.pressure = value }
    fun radiation(value: Float) = apply { this.config.radiation = value }
    fun gas(value: GasConfig) = apply { this.config.gases.add(value) }

    fun build(): PlanetConfig {
        return this.config
    }
}
