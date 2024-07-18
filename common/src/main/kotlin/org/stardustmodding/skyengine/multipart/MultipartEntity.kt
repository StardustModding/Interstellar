package org.stardustmodding.skyengine.multipart

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

abstract class MultipartEntity(type: EntityType<*>, level: Level) : Entity(type, level) {
    private val parts = mutableListOf<EntityPart>()

    fun addPart(part: EntityPart) {
        parts.add(part)
    }

    fun removePart(part: EntityPart) {
        parts.remove(part)
    }

    fun getPart(name: String): EntityPart? {
        return parts.find { it.name == name }
    }

    fun getParts(): List<EntityPart> {
        return parts
    }
}
