package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import org.stardustmodding.interstellar.api.data.NbtSerializable

class AtmosphereState : NbtSerializable<AtmosphereState> {
    var gases: MutableList<GasState> = mutableListOf()

    override fun read(tag: NbtCompound): AtmosphereState {
        val state = AtmosphereState()
        val gases = tag.getList("gases", NbtElement.COMPOUND_TYPE.toInt())

        for (gas in gases) {
            state.gases.add(GasState().read(gas as NbtCompound))
        }

        return state
    }

    override fun write(tag: NbtCompound): NbtCompound {
        val list = NbtList()

        for (gas in gases) {
            list.add(gas.write(NbtCompound()))
        }

        tag.put("gases", list)

        return tag
    }
}
