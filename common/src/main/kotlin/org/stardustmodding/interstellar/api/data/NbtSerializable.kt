package org.stardustmodding.interstellar.api.data

import net.minecraft.nbt.Tag

interface NbtSerializable<T> {
    fun read(tag: Tag): T
    fun write(): Tag
}
