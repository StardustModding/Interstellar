package org.stardustmodding.interstellar.api.planet

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.minecraft.registry.Registries
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier
import net.minecraft.world.dimension.DimensionOptions
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings
import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry
import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.api.serde.IdentifierSerializer

@Serializable
class Planet {
    @Serializable(with = IdentifierSerializer::class)
    @SerialName("dimension_type")
    lateinit var dimensionTypeId: Identifier

    @Serializable(with = IdentifierSerializer::class)
    @SerialName("dimension")
    lateinit var dimensionId: Identifier

    @Serializable(with = IdentifierSerializer::class)
    @SerialName("noise_settings")
    lateinit var noiseSettingsId: Identifier

    @Serializable(with = IdentifierSerializer::class)
    @SerialName("settings")
    lateinit var settingsId: Identifier

    val dimension
        get() = Registries.REGISTRIES.get(RegistryKeys.DIMENSION.registry)?.get(dimensionId) as DimensionOptions?

    val dimensionType
        get() = Registries.REGISTRIES.get(RegistryKeys.DIMENSION_TYPE.registry)?.get(dimensionTypeId) as DimensionType?

    val noiseSettings
        get() = Registries.REGISTRIES.get(RegistryKeys.CHUNK_GENERATOR_SETTINGS.registry)
            ?.get(noiseSettingsId) as ChunkGeneratorSettings?

    val settings get() = InterstellarRegistries.PLANET_SETTINGS.get(settingsId) as PlanetSettings?

    fun create(id: Identifier, registry: DynamicDimensionRegistry) {
        registry.loadDynamicDimension(id, dimension!!.chunkGenerator, dimensionType!!)
    }
}
