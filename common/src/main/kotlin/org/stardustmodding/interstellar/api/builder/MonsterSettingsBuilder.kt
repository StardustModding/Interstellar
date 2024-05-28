package org.stardustmodding.interstellar.api.builder

import net.minecraft.util.valueproviders.IntProvider
import net.minecraft.world.level.dimension.DimensionType

class MonsterSettingsBuilder {
    var piglinSafe: Boolean? = null
    var hasRaids: Boolean? = null
    var lightTest: IntProvider? = null
    var blockLightLimit: Int? = null

    fun piglinSafe(value: Boolean) = apply { this.piglinSafe = value }
    fun hasRaids(value: Boolean) = apply { this.hasRaids = value }
    fun lightTest(value: IntProvider) = apply { this.lightTest = value }
    fun blockLightLimit(value: Int) = apply { this.blockLightLimit = value }

    fun build(): DimensionType.MonsterSettings {
        return DimensionType.MonsterSettings(piglinSafe!!, hasRaids!!, lightTest!!, blockLightLimit!!)
    }
}
