package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.data.SavedState
import org.stardustmodding.interstellar.impl.Interstellar

class PlanetState(override val id: Identifier = Interstellar.id("planet_state")) : SavedState() {
    var terraformation = TerraformationState()

    override fun read(tag: NbtCompound): PlanetState {
        val state = PlanetState()

        state.terraformation = TerraformationState().read(tag.getCompound("terraformation"))

        return state
    }

    override fun write(nbt: NbtCompound): NbtCompound {
        nbt.put("terraformation", terraformation.write())

        return nbt
    }

    override fun default(): SavedState = PlanetState()
}
