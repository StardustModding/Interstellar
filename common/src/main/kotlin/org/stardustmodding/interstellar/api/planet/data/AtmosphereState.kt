package org.stardustmodding.interstellar.api.planet.data

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import org.stardustmodding.interstellar.api.data.NbtSerializable

class AtmosphereState : NbtSerializable<AtmosphereState> {
    var gases: MutableList<GasState> = mutableListOf()

    override fun read(tag: Tag): AtmosphereState {
        val comp = tag as CompoundTag
        val state = AtmosphereState()
        val gases = comp.getList("gases", Tag.TAG_COMPOUND.toInt())

        for (gas in gases) {
            state.gases.add(GasState().read(gas as CompoundTag))
        }

        return state
    }

    override fun write(): CompoundTag {
        val tag = CompoundTag()
        val list = ListTag()

        for (gas in gases) {
            list.add(gas.write())
        }

        tag.put("gases", list)

        return tag
    }
}
