package org.stardustmodding.interstellar.api.gas

import net.minecraft.util.Identifier

// gas.gas.Gas!
class GasData(
    var id: Identifier,

    // This is in kPa
    var amount: Float = 0f
)
