package org.stardustmodding.skyengine.multipart

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.world.World

abstract class MultipartEntity(type: EntityType<*>?, world: World?) : Entity(type, world) {
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
