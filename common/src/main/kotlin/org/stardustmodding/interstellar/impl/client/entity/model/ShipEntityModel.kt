package org.stardustmodding.interstellar.impl.client.entity.model

import net.minecraft.client.model.ModelData
import net.minecraft.client.model.ModelPart
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.util.math.MatrixStack
import org.stardustmodding.interstellar.impl.entity.ShipEntity

class ShipEntityModel(private val parts: MutableList<ModelPart>) : EntityModel<ShipEntity>() {
    fun getTexturedModelData(): TexturedModelData {
        val data = ModelData()

        return TexturedModelData.of(data, 64, 64)
    }

    override fun setAngles(
        entity: ShipEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {

    }

    override fun render(
        matrices: MatrixStack,
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
                matrices,
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
