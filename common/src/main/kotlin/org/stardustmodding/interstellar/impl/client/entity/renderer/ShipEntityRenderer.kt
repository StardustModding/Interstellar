package org.stardustmodding.interstellar.impl.client.entity.renderer

import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.entity.ShipEntity
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.resources.ResourceLocation

class ShipEntityRenderer(ctx: EntityRendererProvider.Context) : EntityRenderer<ShipEntity>(ctx) {

    override fun getTextureLocation(entity: ShipEntity): ResourceLocation {
        return ResourceLocation(Interstellar.MOD_ID, "ship")
    }
}
