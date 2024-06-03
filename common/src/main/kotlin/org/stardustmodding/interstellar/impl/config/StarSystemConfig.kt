package org.stardustmodding.interstellar.impl.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import org.stardustmodding.interstellar.impl.Interstellar

@Config(name = "system")
class StarSystemConfig : ConfigData {
    var id: Int = -1
    var name: String
    var planets: MutableList<Int>

    constructor() : this(Interstellar.MOD_ID + ":null")

    constructor(name: String) {
        this.name = name;
        this.planets = mutableListOf()
    }
}
