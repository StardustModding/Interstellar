package org.stardustmodding.interstellar.impl.client.entity.renderer

import com.mojang.blaze3d.vertex.PoseStack
import foundry.veil.api.client.render.VeilRenderSystem
import foundry.veil.api.client.render.shader.program.ShaderProgram
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.entity.PlanetEntity

class PlanetRenderer(ctx: EntityRendererProvider.Context) : EntityRenderer<PlanetEntity>(ctx) {
    override fun getTextureLocation(entity: PlanetEntity) = textureId()

    override fun render(
        entity: PlanetEntity,
        yaw: Float,
        partialTicks: Float,
        stack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int
    ) {
        val shader = VeilRenderSystem.setShader(PLANET_SHADER) ?: return

        shader.bind()
        ShaderProgram.unbind()
    }

    companion object {
        val PLANET_SHADER = Interstellar.id("planet")

        fun textureId(): ResourceLocation {
            return Interstellar.id("planet")
        }
    }
}
