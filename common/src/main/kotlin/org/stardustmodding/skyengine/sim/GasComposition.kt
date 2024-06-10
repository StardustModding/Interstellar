package org.stardustmodding.skyengine.sim

import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.data.NbtSerializable
import org.stardustmodding.interstellar.api.gas.GasData
import org.stardustmodding.interstellar.api.planet.PlanetSettings

class GasComposition(val gases: MutableList<GasData> = mutableListOf()): NbtSerializable<GasComposition>, Iterable<Identifier> {
    companion object {
        val EMPTY = GasComposition(mutableListOf())

        fun fromSettings(settings: PlanetSettings): GasComposition {
            return GasComposition(settings.gases)
        }
    }

    operator fun get(id: Identifier) = gases.find { it.id == id }?.amount ?: 0f

    operator fun set(id: Identifier, amount: Float) {
        if (gases.find { it.id == id } == null) {
            gases.add(GasData(id, amount))
        } else {
            gases.find { it.id == id }!!.amount = amount
        }
    }

    override fun read(tag: NbtElement): GasComposition {
        val comp = GasComposition()
        val list = tag as NbtList

        for (item in list) {
            comp.gases.add(GasData().read(item))
        }

        return comp
    }

    override fun write(): NbtElement {
        val list = NbtList()

        for (gas in gases) {
            list.add(gas.write())
        }

        return list
    }

    override fun iterator(): Iterator<Identifier> = gases.map { it.id }.iterator()
}
