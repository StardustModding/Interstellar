package org.stardustmodding.interstellar.impl.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.impl.Interstellar

@Config(name = "gases")
class GasConfig : ConfigData {
    var id: String
    var amount: Float

    constructor() : this(Interstellar.MOD_ID, "null", -1.0f)

    constructor(id: String, amount: Float) {
        this.id = id
        this.amount = amount
    }

    constructor(id: Identifier, amount: Float) {
        this.id = id.toString()
        this.amount = amount
    }

    constructor(namespace: String, id: String, amount: Float) {
        this.id = "$namespace:$id"
        this.amount = amount
    }
}
