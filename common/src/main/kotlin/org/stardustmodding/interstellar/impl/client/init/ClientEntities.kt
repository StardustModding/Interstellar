package org.stardustmodding.interstellar.impl.client.init

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry
import dev.architectury.registry.client.level.entity.EntityRendererRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.ModelLayerLocation
import org.stardustmodding.interstellar.api.init.InitializedClient
import org.stardustmodding.interstellar.impl.client.entity.model.ShipModel
import org.stardustmodding.interstellar.impl.client.entity.renderer.PlanetRenderer
import org.stardustmodding.interstellar.impl.client.entity.renderer.ShipRenderer
import org.stardustmodding.interstellar.impl.init.Entities

@Environment(EnvType.CLIENT)
object ClientEntities : InitializedClient {
    private var MODEL_SHIP_LAYER = ModelLayerLocation(ShipRenderer.textureId(), "main")

    override fun init(it: Minecraft) {
        EntityRendererRegistry.register({ Entities.SHIP }) { ctx ->
            ShipRenderer(ctx)
        }

        EntityRendererRegistry.register({ Entities.PLANET }) { ctx ->
            PlanetRenderer(ctx)
        }

        EntityModelLayerRegistry.register(MODEL_SHIP_LAYER, ShipModel::getTexturedModelData)
    }
}
