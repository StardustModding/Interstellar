package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.data.NbtSerializable

class GasState : NbtSerializable<GasState> {
    lateinit var id: Identifier

    var amount = 0f

    override fun read(tag: NbtCompound): GasState {
        val state = GasState()

        state.id = Identifier.tryParse(tag.getString("id"))!!
        amount = tag.getFloat("amount")

        return state
    }

    override fun write(tag: NbtCompound): NbtCompound {
        tag.putString("id", id.toString())
        tag.putFloat("amount", amount)

        return tag
    }
}