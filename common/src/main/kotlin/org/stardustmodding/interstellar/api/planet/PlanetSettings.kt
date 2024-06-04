package org.stardustmodding.interstellar.api.planet

import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.gas.GasData
import org.stardustmodding.interstellar.impl.config.PlanetConfig

class PlanetSettings {
    // In μSv/tick
    var radiation = 0.0f
    var tier = 0
    var checkOxygen = true
    var checkPressure = true
    var checkRadiation = true
    var gases: MutableList<GasData> = mutableListOf()

    val pressure get() = gases.sumOf { it.amount.toDouble() }.toFloat()

    companion object {
        fun fromConfig(it: PlanetConfig): PlanetSettings {
            return PlanetSettings().apply {
                radiation = it.radiation
                tier = it.tier
                checkOxygen = it.enableOxygen
                checkPressure = it.enablePressure
                checkRadiation = it.enableRadiation
                gases = it.gases.map { GasData(Identifier.tryParse(it.id)!!, it.amount) }.toMutableList()
            }
        }
    }
}
