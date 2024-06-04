package org.stardustmodding.interstellar.impl.client.init

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry
import dev.architectury.registry.client.level.entity.EntityRendererRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.entity.model.EntityModelLayer
import org.stardustmodding.interstellar.api.init.InitializedClient
import org.stardustmodding.interstellar.impl.client.entity.model.ShipEntityModel
import org.stardustmodding.interstellar.impl.client.entity.renderer.ShipEntityRenderer
import org.stardustmodding.interstellar.impl.init.Entities

@Environment(EnvType.CLIENT)
object ClientEntities : InitializedClient {
    var MODEL_SHIP_LAYER = EntityModelLayer(ShipEntityRenderer.textureId(), "main")

    override fun init(it: MinecraftClient) {
        EntityRendererRegistry.register({ Entities.SHIP }) { ctx ->
            ShipEntityRenderer(ctx)
        }

        EntityModelLayerRegistry.register(MODEL_SHIP_LAYER, ShipEntityModel::getTexturedModelData)
    }
}
