package org.stardustmodding.interstellar.api.gas

import kotlinx.serialization.Serializable
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.data.NbtSerializable
import org.stardustmodding.interstellar.api.serde.IdentifierSerializer
import org.stardustmodding.interstellar.impl.Interstellar.id

// gas.gas.Gas!
@Serializable
class GasData(
    @Serializable(with = IdentifierSerializer::class)
    var id: Identifier = id("empty"),

    // This is in kPa
    var amount: Float = 0f
) : NbtSerializable<GasData> {
    override fun read(tag: NbtElement): GasData {
        val comp = tag as NbtCompound
        val id = Identifier.tryParse(comp.getString("id"))!!
        val amount = comp.getFloat("amount")

        return GasData(id, amount)
    }

    override fun write(): NbtElement {
        val comp = NbtCompound()

        comp.putString("id", id.toString())
        comp.putFloat("amount", amount)

        return comp
    }
}
