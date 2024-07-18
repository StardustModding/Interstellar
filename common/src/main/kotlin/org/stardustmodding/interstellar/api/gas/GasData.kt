package org.stardustmodding.interstellar.api.gas

import kotlinx.serialization.Serializable
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.interstellar.api.data.NbtSerializable
import org.stardustmodding.interstellar.api.serde.ResourceLocationSerializer
import org.stardustmodding.interstellar.impl.Interstellar.id

// gas.gas.Gas!
@Serializable
class GasData(
    @Serializable(with = ResourceLocationSerializer::class)
    var id: ResourceLocation = id("empty"),

    // This is in kPa
    var amount: Float = 0f
) : NbtSerializable<GasData> {
    override fun read(tag: Tag): GasData {
        val comp = tag as CompoundTag
        val id = ResourceLocation.tryParse(comp.getString("id"))!!
        val amount = comp.getFloat("amount")

        return GasData(id, amount)
    }

    override fun write(): Tag {
        val comp = CompoundTag()

        comp.putString("id", id.toString())
        comp.putFloat("amount", amount)

        return comp
    }
}
