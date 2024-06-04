package org.stardustmodding.interstellar.api.starsystem

import kotlinx.serialization.Serializable
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.api.serde.IdentifierSerializer

@Serializable
class StarSystem {
    var planets: MutableList<@Serializable(with = IdentifierSerializer::class) Identifier> = mutableListOf()
}
