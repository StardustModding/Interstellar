package org.stardustmodding.interstellar.api.gas

import kotlinx.serialization.Serializable
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.serde.IdentifierSerializer

// gas.gas.Gas!
@Serializable
class GasData(
    @Serializable(with = IdentifierSerializer::class)
    var id: Identifier,

    // This is in kPa
    var amount: Float = 0f
)
