package org.stardustmodding.interstellar.api.builder

import net.minecraft.block.Block
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.world.dimension.DimensionType
import java.util.*

class DimensionTypeBuilder {
    private var fixedTime = OptionalLong.empty()
    private var hasSkyLight: Boolean? = null
    private var hasCeiling: Boolean? = null
    private var ultraWarm: Boolean? = null
    private var natural: Boolean? = null
    private var coordinateScale: Double? = null
    private var bedWorks: Boolean? = null
    private var respawnAnchorWorks: Boolean? = null
    private var minY: Int? = null
    private var height: Int? = null
    private var logicalHeight: Int? = null
    private var infiniburn: TagKey<Block>? = null
    private var effectsLocation: Identifier? = null
    private var ambientLight: Float? = null
    private var monsterSettings: DimensionType.MonsterSettings? = null

    fun fixedTime(value: OptionalLong) = apply { this.fixedTime = value }
    fun hasSkyLight(value: Boolean) = apply { this.hasSkyLight = value }
    fun hasCeiling(value: Boolean) = apply { this.hasCeiling = value }
    fun ultraWarm(value: Boolean) = apply { this.ultraWarm = value }
    fun natural(value: Boolean) = apply { this.natural = value }
    fun coordinateScale(value: Double) = apply { this.coordinateScale = value }
    fun bedWorks(value: Boolean) = apply { this.bedWorks = value }
    fun respawnAnchorWorks(value: Boolean) = apply { this.respawnAnchorWorks = value }
    fun minY(value: Int) = apply { this.minY = value }
    fun height(value: Int) = apply { this.height = value }
    fun logicalHeight(value: Int) = apply { this.logicalHeight = value }
    fun infiniburn(value: TagKey<Block>) = apply { this.infiniburn = value }
    fun effectsLocation(value: Identifier) = apply { this.effectsLocation = value }
    fun ambientLight(value: Float) = apply { this.ambientLight = value }
    fun monsterSettings(value: DimensionType.MonsterSettings) = apply { this.monsterSettings = value }

    fun build(): DimensionType {
        return DimensionType(
            fixedTime,
            hasSkyLight!!,
            hasCeiling!!,
            ultraWarm!!,
            natural!!,
            coordinateScale!!,
            bedWorks!!,
            respawnAnchorWorks!!,
            minY!!,
            height!!,
            logicalHeight!!,
            infiniburn!!,
            effectsLocation!!,
            ambientLight!!,
            monsterSettings!!,
        )
    }
}
