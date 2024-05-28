package org.stardustmodding.interstellar.impl.client.entity.renderer

import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.util.Identifier
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.entity.ShipEntity

class ShipEntityRenderer(ctx: EntityRendererFactory.Context) : EntityRenderer<ShipEntity>(ctx) {

    override fun getTexture(entity: ShipEntity): Identifier {
        return Interstellar.id("ship")
    }
}
