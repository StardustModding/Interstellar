package org.stardustmodding.interstellar.impl.entity

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

class ShipEntity(type: EntityType<*>, level: Level) : Entity(type, level) {
    val blocks: MutableList<BlockState> = mutableListOf()

    override fun defineSynchedData() {

    }

    override fun readAdditionalSaveData(compound: CompoundTag) {

    }

    override fun addAdditionalSaveData(compound: CompoundTag) {

    }
}
