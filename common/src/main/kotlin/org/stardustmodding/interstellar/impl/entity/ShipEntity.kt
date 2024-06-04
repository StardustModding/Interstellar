package org.stardustmodding.interstellar.impl.entity

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.registry.Registries
import net.minecraft.registry.SimpleDefaultedRegistry
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ShipEntity(type: EntityType<*>, world: World) : Entity(type, world) {
    val blocks: MutableList<Pair<BlockState, BlockPos>> = mutableListOf()

    override fun initDataTracker() {

    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        blocks.clear()

        val list = nbt.getList("blocks", NbtElement.COMPOUND_TYPE.toInt())
        val lookup = (Registries.BLOCK as SimpleDefaultedRegistry<Block>).createMutableEntryLookup()

        for (rawItem in list) {
            val item = rawItem as NbtCompound
            val state = NbtHelper.toBlockState(lookup, item.getCompound("state"))
            val pos = NbtHelper.toBlockPos(item.getCompound("pos"))

            blocks.add(Pair(state, pos))
        }
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        val list = NbtList()

        for (item in blocks) {
            val compound = NbtCompound()

            compound.put("state", NbtHelper.fromBlockState(item.first))
            compound.put("pos", NbtHelper.fromBlockPos(item.second))

            list.add(compound)
        }

        nbt.put("blocks", list)
    }
}
