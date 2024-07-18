package org.stardustmodding.skyengine.sim

import net.minecraft.nbt.Tag
import net.minecraft.nbt.ListTag
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.interstellar.api.data.NbtSerializable
import org.stardustmodding.interstellar.api.gas.GasData
import org.stardustmodding.interstellar.api.planet.PlanetSettings

class GasComposition(val gases: MutableList<GasData> = mutableListOf()) : NbtSerializable<GasComposition>,
    Iterable<ResourceLocation> {
    companion object {
        val EMPTY = GasComposition(mutableListOf())

        fun fromSettings(settings: PlanetSettings): GasComposition {
            return GasComposition(settings.gases)
        }
    }

    operator fun get(id: ResourceLocation) = gases.find { it.id == id }?.amount ?: 0f

    operator fun set(id: ResourceLocation, amount: Float) {
        if (gases.find { it.id == id } == null) {
            gases.add(GasData(id, amount))
        } else {
            gases.find { it.id == id }!!.amount = amount
        }
    }

    override fun read(tag: Tag): GasComposition {
        val comp = GasComposition()
        val list = tag as ListTag

        for (item in list) {
            comp.gases.add(GasData().read(item))
        }

        return comp
    }

    override fun write(): Tag {
        val list = ListTag()

        for (gas in gases) {
            list.add(gas.write())
        }

        return list
    }

    override fun iterator(): Iterator<ResourceLocation> = gases.map { it.id }.iterator()
}
