package org.stardustmodding.interstellar.impl.client.entity.renderer

import com.mojang.blaze3d.vertex.PoseStack
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.client.entity.model.ShipModel
import org.stardustmodding.interstellar.impl.entity.ShipEntity

@Environment(EnvType.CLIENT)
class ShipRenderer(ctx: EntityRendererProvider.Context) : EntityRenderer<ShipEntity>(ctx) {
    private val model = ShipModel()

    override fun getTextureLocation(entity: ShipEntity) = textureId()

    override fun render(
        entity: ShipEntity,
        yaw: Float,
        tickDelta: Float,
        matrices: PoseStack,
        vertexConsumers: MultiBufferSource,
        light: Int
    ) {
        val layer = model.renderType(getTextureLocation(entity))
        val consumer = vertexConsumers.getBuffer(layer)

        model.setupAnim(entity, tickDelta, 0f, -0.1f, 0f, 0f)
        model.renderToBuffer(matrices, consumer, light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f)

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
    }

    companion object {
        fun textureId(): ResourceLocation {
            return Interstellar.id("ship")
        }
    }
}
