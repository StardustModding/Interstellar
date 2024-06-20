package org.stardustmodding.interstellar.impl.client.entity.model

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.ModelData
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.BlockPos
import org.stardustmodding.interstellar.impl.entity.ShipEntity
import org.stardustmodding.skyengine.math.PosExt.toBlockPos
import org.stardustmodding.skyengine.math.PosExt.toVec3d

@Environment(EnvType.CLIENT)
class ShipEntityModel : EntityModel<ShipEntity>() {
    private var entity: ShipEntity? = null

    private fun renderBlockMatrix(
        relPos: BlockPos,
        state: BlockState,
        stack: MatrixStack,
        buffer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        red: Float,
        green: Float,
        blue: Float
    ) {
        val client = MinecraftClient.getInstance()
        val mgr = client.blockRenderManager

        stack.push()
        stack.translate(relPos.x.toDouble(), relPos.y.toDouble(), relPos.z.toDouble())

        mgr.renderBlock(state, entity!!.pos.add(relPos.toVec3d()).toBlockPos(), entity!!.world, stack, buffer, false, entity!!.world.random)

        stack.pop()
    }

    private fun renderBlockMatrices(
        blocks: Map<BlockPos, BlockState>,
        stack: MatrixStack,
        buffer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        red: Float,
        green: Float,
        blue: Float
    ) {
        for (block in blocks) {
            renderBlockMatrix(block.key, block.value, stack, buffer, packedLight, packedOverlay, red, green, blue)
        }
    }

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
        matrices: MatrixStack,
        buffer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float
    ) {
        if (entity != null) {
            renderBlockMatrices(
                entity!!.blocks?.get() ?: mapOf(),
                matrices,
                buffer,
                packedLight,
                packedOverlay,
                red,
                green,
                blue
            )
        }
    }

    companion object {
        fun getTexturedModelData(): TexturedModelData {
            return TexturedModelData.of(ModelData(), 0, 0)
        }
    }
}
