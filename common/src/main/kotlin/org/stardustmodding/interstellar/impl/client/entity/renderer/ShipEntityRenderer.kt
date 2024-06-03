package org.stardustmodding.interstellar.impl.client.entity.renderer

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.client.entity.model.ShipEntityModel
import org.stardustmodding.interstellar.impl.entity.ShipEntity

@Environment(EnvType.CLIENT)
class ShipEntityRenderer(ctx: EntityRendererFactory.Context) : EntityRenderer<ShipEntity>(ctx) {
    private val model = ShipEntityModel()

    override fun getTexture(entity: ShipEntity): Identifier {
        return textureId()
    }

    override fun render(
        entity: ShipEntity,
        yaw: Float,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int
    ) {
        val layer = model.getLayer(getTexture(entity))
        val consumer = vertexConsumers.getBuffer(layer)

        model.setAngles(entity, tickDelta, 0f, -0.1f, 0f, 0f)
        model.render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f)

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
    }

    companion object {
        fun textureId(): Identifier {
            return Interstellar.id("ship")
        }
    }
}
