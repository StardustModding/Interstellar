package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.NbtCompound
import org.stardustmodding.interstellar.api.data.NbtSerializable

class TerraformationState : NbtSerializable<TerraformationState> {
    var atmosphere = AtmosphereState()
    var water = WaterState()
    var terraformable = false

    override fun read(tag: NbtCompound): TerraformationState {
        val state = TerraformationState()

        state.atmosphere = AtmosphereState().read(tag.getCompound("atmosphere"))
        state.water = WaterState().read(tag.getCompound("water"))
        state.terraformable = tag.getBoolean("terraformable")

        return state
    }

    override fun write(tag: NbtCompound): NbtCompound {
        tag.put("atmosphere", atmosphere.write(NbtCompound()))
        tag.put("water", water.write(NbtCompound()))
        tag.putBoolean("terraformable", terraformable)

        return tag
    }
}
