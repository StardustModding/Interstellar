package org.stardustmodding.interstellar.api.data

import net.minecraft.nbt.NbtCompound

interface NbtSerializable<T> {
    fun read(tag: NbtCompound): T
    fun write(tag: NbtCompound): NbtCompound
}
