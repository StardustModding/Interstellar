package org.stardustmodding.interstellar.api.starsystem

import kotlinx.serialization.Serializable
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.interstellar.api.serde.ResourceLocationSerializer

@Serializable
class StarSystem {
    var planets: MutableList<@Serializable(with = ResourceLocationSerializer::class) ResourceLocation> = mutableListOf()
}
