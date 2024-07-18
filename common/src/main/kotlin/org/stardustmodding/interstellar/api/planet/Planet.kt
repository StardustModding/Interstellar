package org.stardustmodding.interstellar.api.planet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import org.stardustmodding.interstellar.api.registry.InterstellarRegistries
import org.stardustmodding.interstellar.api.serde.ResourceLocationSerializer

@Serializable
class Planet {
    @Serializable(with = ResourceLocationSerializer::class)
    @SerialName("dimension")
    lateinit var dimensionId: ResourceLocation

    @Serializable(with = ResourceLocationSerializer::class)
    @SerialName("settings")
    lateinit var settingsId: ResourceLocation

    val settings get() = InterstellarRegistries.PLANET_SETTINGS.get(settingsId) as PlanetSettings?

    fun tick(world: ServerLevel) {
        if (world.dimensionTypeId().location() == dimensionId) {
            // Do stuff
        }
    }
}
