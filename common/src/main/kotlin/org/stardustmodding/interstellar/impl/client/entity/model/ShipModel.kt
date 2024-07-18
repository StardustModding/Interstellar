package org.stardustmodding.interstellar.impl.client.entity.model

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.Minecraft
import net.minecraft.client.model.EntityModel
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import org.stardustmodding.interstellar.impl.entity.ShipEntity
import org.stardustmodding.skyengine.math.PosExt.toBlockPos
import org.stardustmodding.skyengine.math.PosExt.toVec3d

@Environment(EnvType.CLIENT)
class ShipModel : EntityModel<ShipEntity>() {
    private var entity: ShipEntity? = null

    override fun setupAnim(
        entity: ShipEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        this.entity = entity
    }

    override fun renderToBuffer(
        stack: PoseStack,
        buffer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float
    ) {
        if (entity != null) {
            val blocks = entity!!.blocks?.get() ?: mapOf()
            val client = Minecraft.getInstance()
            val mgr = client.blockRenderer

            for ((relPos, state) in blocks) {
                stack.pushPose()
                stack.translate(relPos.x.toDouble(), relPos.y.toDouble(), relPos.z.toDouble())
                mgr.renderBatched(state, entity!!.position.add(relPos.toVec3d()).toBlockPos(), entity!!.level, stack, buffer, false, entity!!.level.random)
                stack.popPose()
            }
        }
    }

    companion object {
        fun getTexturedModelData(): LayerDefinition {
            return LayerDefinition.create(MeshDefinition(), 0, 0)
        }
    }
}
