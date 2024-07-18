package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.interstellar.api.data.NbtSerializable

class GasState : NbtSerializable<GasState> {
    lateinit var id: ResourceLocation

    var amount = 0f

    override fun read(tag: Tag): GasState {
        val comp = tag as CompoundTag
        val state = GasState()

        state.id = ResourceLocation.tryParse(comp.getString("id"))!!
        amount = comp.getFloat("amount")

        return state
    }

    override fun write(): CompoundTag {
        val tag = CompoundTag()

        tag.putString("id", id.toString())
        tag.putFloat("amount", amount)

        return tag
    }
}