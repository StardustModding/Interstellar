package org.stardustmodding.interstellar.impl.client.entity.model

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.ModelData
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.util.math.MatrixStack
import org.stardustmodding.interstellar.impl.entity.ShipEntity
import org.stardustmodding.skyengine.math.PosExt.toBlockPos
import org.stardustmodding.skyengine.math.PosExt.toVec3d

@Environment(EnvType.CLIENT)
class ShipEntityModel : EntityModel<ShipEntity>() {
    private var entity: ShipEntity? = null

    override fun setAngles(
        entity: ShipEntity,
        limbSwing: Float,
        limbSwingAmount: Float,
        ageInTicks: Float,
        netHeadYaw: Float,
        headPitch: Float
    ) {
        this.entity = entity
    }

    override fun render(
        stack: MatrixStack,
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
            val client = MinecraftClient.getInstance()
            val mgr = client.blockRenderManager

            for ((relPos, state) in blocks) {
                println("Rendering block at $relPos: ${state.registryEntry.key}")
                stack.push()
                stack.translate(relPos.x.toDouble(), relPos.y.toDouble(), relPos.z.toDouble())
                mgr.renderBlock(state, entity!!.pos.add(relPos.toVec3d()).toBlockPos(), entity!!.world, stack, buffer, false, entity!!.world.random)
                stack.pop()
            }
        }
    }

    companion object {
        fun getTexturedModelData(): TexturedModelData {
            return TexturedModelData.of(ModelData(), 0, 0)
        }
    }
}
