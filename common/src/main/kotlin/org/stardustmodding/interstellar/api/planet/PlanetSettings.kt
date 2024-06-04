package org.stardustmodding.interstellar.api.planet

import kotlinx.serialization.Serializable
import org.stardustmodding.interstellar.api.gas.GasData

@Serializable
class PlanetSettings {
    var enabled = true

    // In μSv/tick
    var radiation = 0f
    var tier = 0
    var checkOxygen = true
    var checkPressure = true
    var checkRadiation = true
    var gases: MutableList<GasData> = mutableListOf()

    val pressure get() = gases.sumOf { it.amount.toDouble() }.toFloat()
}
