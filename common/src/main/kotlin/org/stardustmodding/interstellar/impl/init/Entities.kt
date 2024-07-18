package org.stardustmodding.interstellar.impl.init

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.entity.PlanetEntity
import org.stardustmodding.interstellar.impl.entity.ShipEntity

object Entities {
    var SHIP: EntityType<ShipEntity> = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        Interstellar.id("ship"),
        EntityType.Builder.of({ type, level ->
            ShipEntity(type, level)
        }, MobCategory.MISC).build("ship")
    )

    var PLANET: EntityType<PlanetEntity> = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        Interstellar.id("planet"),
        EntityType.Builder.of({ type, level ->
            PlanetEntity(type, level)
        }, MobCategory.MISC).build("planet")
    )
}
