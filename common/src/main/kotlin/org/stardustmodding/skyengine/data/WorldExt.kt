package org.stardustmodding.skyengine.data

import net.minecraft.server.level.ServerLevel
import net.minecraft.core.BlockPos
import org.stardustmodding.interstellar.api.data.StateUtil
import org.stardustmodding.interstellar.api.planet.PlanetSettings
import org.stardustmodding.interstellar.api.registry.InterstellarRegistries
import org.stardustmodding.skyengine.sim.GasComposition
import org.stardustmodding.skyengine.sim.PressureState

object WorldExt {
    private val worldStates = mutableMapOf<ServerLevel, PressureState>()

    fun ServerLevel.getPressureState(pos: BlockPos, defer: Boolean = false): GasComposition {
        val block = getBlockState(pos)

        if (!block.isAir) return GasComposition.EMPTY

        val settings = InterstellarRegistries.PLANETS.find { it.dimensionId == dimensionTypeId().location() }?.settings
            ?: PlanetSettings.DEFAULT_SETTINGS

        val default = GasComposition.fromSettings(settings)
        val states = worldStates.getOrPut(this) { StateUtil.load<PressureState>(this) }
        val state = states.getOrPut(pos) { default }

        if (!defer) StateUtil.write(this, states)

        return state
    }

    fun ServerLevel.setPressureState(pos: BlockPos, state: GasComposition, defer: Boolean = false) {
        val states = worldStates.getOrPut(this) { StateUtil.load<PressureState>(this) }

        states[pos] = state

        if (!defer) StateUtil.write(this, states)
    }

    fun ServerLevel.savePressureState() {
        StateUtil.write(this, worldStates.getOrPut(this) { StateUtil.load<PressureState>(this) })
    }
}
