package org.stardustmodding.interstellar.api.data

import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.world.PersistentState
import kotlin.reflect.full.primaryConstructor

abstract class SavedState: PersistentState() {
    abstract fun read(tag: NbtCompound): SavedState
    abstract fun write(tag: NbtCompound): NbtCompound
    abstract fun default(): SavedState
    abstract val id: Identifier

    override fun writeNbt(nbt: NbtCompound): NbtCompound = write(nbt)

    companion object {
        inline fun <reified T>readStatic(tag: NbtCompound): T where T: SavedState {
            val temp = T::class.primaryConstructor!!.call()

            return temp.read(tag) as T
        }

        inline fun <reified T>defaultStatic(): T where T: SavedState {
            val temp = T::class.primaryConstructor!!.call()

            return temp.default() as T
        }

        inline fun <reified T>idStatic(): Identifier where T: SavedState {
            val temp = T::class.primaryConstructor!!.call()

            return temp.id
        }
    }
}
