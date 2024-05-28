package org.stardustmodding.interstellar.impl.init

import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.entity.ShipEntity
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory

object Entities {
    var SHIP: EntityType<ShipEntity>? = null

    fun register() {
        SHIP = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation(Interstellar.MOD_ID, "ship"),
            EntityType.Builder.of({ type, level ->
                ShipEntity(type, level)
            }, MobCategory.MISC).build("ship")
        )
    }
}
