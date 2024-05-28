package org.stardustmodding.interstellar.impl.entity

import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.world.World

class ShipEntity(type: EntityType<*>, world: World) : Entity(type, world) {
    val blocks: MutableList<BlockState> = mutableListOf()

    override fun initDataTracker() {

    }

    override fun readCustomDataFromNbt(nbt: NbtCompound?) {

    }

    override fun writeCustomDataToNbt(nbt: NbtCompound?) {

    }
}
