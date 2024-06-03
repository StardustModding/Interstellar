package org.stardustmodding.interstellar.impl.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import org.stardustmodding.interstellar.impl.Interstellar

@Config(name = "planet")
class PlanetConfig : ConfigData {
    var enable: Boolean = false
    var dimensionId: Int = -1
    var name: String

    // This is in kPa
    var pressure: Float = 0.0f

    // In μSv/tick
    var radiation: Float = 0.0f
    var tier: Int = 0
    var enableOxygen: Boolean = true
    var enablePressure: Boolean = true
    var enableRadiation: Boolean = true
    var gases: MutableList<GasConfig>

    constructor() : this(Interstellar.MOD_ID + ":null")

    constructor(name: String) {
        this.name = name
        this.gases = mutableListOf()
    }
}
