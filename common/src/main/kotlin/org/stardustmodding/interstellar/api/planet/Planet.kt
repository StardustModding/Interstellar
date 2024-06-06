package org.stardustmodding.interstellar.api.planet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.registry.InterstellarRegistries
import org.stardustmodding.interstellar.api.serde.IdentifierSerializer

@Serializable
class Planet {
    @Serializable(with = IdentifierSerializer::class)
    @SerialName("dimension")
    lateinit var dimensionId: Identifier

    @Serializable(with = IdentifierSerializer::class)
    @SerialName("settings")
    lateinit var settingsId: Identifier

    val settings get() = InterstellarRegistries.PLANET_SETTINGS.get(settingsId) as PlanetSettings?

    fun tick(world: ServerWorld) {
        if (world.dimensionKey.value == dimensionId) {
            // Do stuff
        }
    }
}
