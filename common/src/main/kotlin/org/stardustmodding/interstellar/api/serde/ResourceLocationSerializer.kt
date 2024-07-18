package org.stardustmodding.interstellar.api.serde

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minecraft.resources.ResourceLocation

class ResourceLocationSerializer : KSerializer<ResourceLocation> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ResourceLocation", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ResourceLocation) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): ResourceLocation {
        return ResourceLocation.tryParse(decoder.decodeString())!!
    }
}