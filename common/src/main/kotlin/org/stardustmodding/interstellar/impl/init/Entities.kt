package org.stardustmodding.interstellar.impl.init

import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKeys
import net.minecraft.server.MinecraftServer
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.entity.ShipEntity

object Entities {
    var SHIP: EntityType<ShipEntity>? = null

    fun register(server: MinecraftServer) {
        SHIP = Registry.register(
            server.registryManager.get(RegistryKeys.ENTITY_TYPE),
            Interstellar.id("ship"),
            EntityType.Builder.create({ type, level ->
                ShipEntity(type, level)
            }, SpawnGroup.MISC).build("ship")
        )
    }
}
