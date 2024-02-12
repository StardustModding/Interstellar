package dev.niostone.interstellar.impl.config

import dev.niostone.interstellar.impl.Interstellar
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config

@Config(name = "system")
class StarSystemConfig: ConfigData {
    var id: Int = -1
    var name: String
    var planets: MutableList<Int>

    constructor() : this(Interstellar.MOD_ID + ":null")

    constructor(name: String) {
        this.name = name;
        this.planets = mutableListOf()
    }
}
