package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.data.NbtSerializable

class GasState : NbtSerializable<GasState> {
    lateinit var id: Identifier

    var amount = 0f

    override fun read(tag: NbtElement): GasState {
        val comp = tag as NbtCompound
        val state = GasState()

        state.id = Identifier.tryParse(comp.getString("id"))!!
        amount = comp.getFloat("amount")

        return state
    }

    override fun write(): NbtCompound {
        val tag = NbtCompound()

        tag.putString("id", id.toString())
        tag.putFloat("amount", amount)

        return tag
    }
}