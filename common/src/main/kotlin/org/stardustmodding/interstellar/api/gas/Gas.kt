package org.stardustmodding.interstellar.api.gas

import net.minecraft.util.Identifier

// gas.gas.Gas!
class Gas(
    var id: Identifier,

    // This is in kPa
    var amount: Float = 0f
)
