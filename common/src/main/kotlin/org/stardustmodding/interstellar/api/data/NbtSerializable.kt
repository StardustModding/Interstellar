package org.stardustmodding.interstellar.api.data

import net.minecraft.nbt.NbtElement

interface NbtSerializable<T> {
    fun read(tag: NbtElement): T
    fun write(): NbtElement
}
