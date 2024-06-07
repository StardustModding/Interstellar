package org.stardustmodding.interstellar.api.planet

import kotlinx.serialization.Serializable
import org.stardustmodding.interstellar.api.gas.GasData
import org.stardustmodding.interstellar.api.registry.InterstellarRegistries
import org.stardustmodding.interstellar.impl.Interstellar.id

@Serializable
class PlanetSettings {
    var enabled = true

    var radiation = 0f // In μSv/tick
    var tier = 0
    var checkOxygen = true
    var checkPressure = true
    var checkRadiation = true
    var gravity = 0f // In m/s^2
    var gases: MutableList<GasData> = mutableListOf()

    val pressure get() = gases.sumOf { it.amount.toDouble() }.toFloat()

    companion object {
        val DEFAULT_SETTINGS get() = InterstellarRegistries.PLANET_SETTINGS.get(id("earth"))
    }
}
