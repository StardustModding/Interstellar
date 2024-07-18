package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import org.stardustmodding.interstellar.api.data.NbtSerializable

class TerraformationState : NbtSerializable<TerraformationState> {
    var atmosphere = AtmosphereState()
    var water = WaterState()
    var terraformable = false

    override fun read(tag: Tag): TerraformationState {
        val comp = tag as CompoundTag
        val state = TerraformationState()

        state.atmosphere = AtmosphereState().read(comp.getCompound("atmosphere"))
        state.water = WaterState().read(comp.getCompound("water"))
        state.terraformable = comp.getBoolean("terraformable")

        return state
    }

    override fun write(): CompoundTag {
        val tag = CompoundTag()

        tag.put("atmosphere", atmosphere.write())
        tag.put("water", water.write())
        tag.putBoolean("terraformable", terraformable)

        return tag
    }
}
