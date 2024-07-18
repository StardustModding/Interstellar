package org.stardustmodding.interstellar.impl.entity

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

class PlanetEntity(type: EntityType<*>, level: Level) : Entity(type, level) {
    override fun defineSynchedData() {

    }

    override fun readAdditionalSaveData(compound: CompoundTag) {

    }

    override fun addAdditionalSaveData(compound: CompoundTag) {

    }
}
