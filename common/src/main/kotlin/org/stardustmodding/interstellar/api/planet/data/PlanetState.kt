package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.interstellar.api.data.SavedState
import org.stardustmodding.interstellar.impl.Interstellar

class PlanetState(override val id: ResourceLocation = Interstellar.id("planet_state")) : SavedState() {
    var terraformation = TerraformationState()

    override fun read(tag: CompoundTag): PlanetState {
        val state = PlanetState()

        state.terraformation = TerraformationState().read(tag.getCompound("terraformation"))

        return state
    }

    override fun write(nbt: CompoundTag): CompoundTag {
        nbt.put("terraformation", terraformation.write())

        return nbt
    }

    override fun default(): SavedState = PlanetState()
}
