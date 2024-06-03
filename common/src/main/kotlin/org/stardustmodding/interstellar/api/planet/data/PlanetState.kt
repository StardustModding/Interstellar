package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.data.NbtSerializable
import org.stardustmodding.interstellar.api.data.SavedState
import org.stardustmodding.interstellar.impl.Interstellar

class PlanetState(override val id: Identifier = Interstellar.id("planet_state")) : SavedState(),
    NbtSerializable<PlanetState> {
    var terraformation = TerraformationState()

    override fun read(tag: NbtCompound): PlanetState {
        val state = PlanetState()

        state.terraformation = TerraformationState().read(tag.getCompound("terraformation"))

        return state
    }

    override fun write(tag: NbtCompound): NbtCompound {
        tag.put("terraformation", terraformation.write(NbtCompound()))

        return tag
    }

    override fun default(): SavedState = PlanetState()
}
