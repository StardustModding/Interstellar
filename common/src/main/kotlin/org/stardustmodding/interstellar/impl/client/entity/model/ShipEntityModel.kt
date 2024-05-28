package org.stardustmodding.interstellar.impl.client.entity.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import org.stardustmodding.interstellar.impl.entity.ShipEntity
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition

class ShipEntityModel(private val parts: MutableList<ModelPart>) : EntityModel<ShipEntity>() {
    fun getTexturedModelData(): LayerDefinition {
        val data = MeshDefinition()

        return LayerDefinition.create(data, 64, 64)
    }

    override fun setupAnim(
        entity: ShipEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {

    }

    override fun renderToBuffer(
        poseStack: PoseStack,
        buffer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float
    ) {
        parts.forEach {
            it.render(
                poseStack,
                buffer,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
            )
        }
    }
}
