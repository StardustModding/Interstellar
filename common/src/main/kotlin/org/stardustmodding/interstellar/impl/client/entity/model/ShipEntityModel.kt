package org.stardustmodding.interstellar.impl.client.entity.model

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.BlockState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.ModelData
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.block.BlockModelRenderer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.BlockPos
import org.stardustmodding.interstellar.impl.entity.ShipEntity

@Environment(EnvType.CLIENT)
class ShipEntityModel : EntityModel<ShipEntity>() {
    private var entity: ShipEntity? = null

    private fun renderBlockMatrix(
        state: BlockState,
        relPos: BlockPos,
        stack: MatrixStack,
        buffer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        red: Float,
        green: Float,
        blue: Float
    ) {
        val client = MinecraftClient.getInstance()
        val model = client.bakedModelManager.blockModels.getModel(state)
        val renderer = BlockModelRenderer(client.blockColors)

        stack.translate(relPos.x.toDouble(), relPos.y.toDouble(), relPos.z.toDouble())
        renderer.render(stack.peek(), buffer, state, model, red, green, blue, packedLight, packedOverlay)
        stack.translate(-relPos.x.toDouble(), -relPos.y.toDouble(), -relPos.z.toDouble())
    }

    private fun renderBlockMatrices(
        blocks: List<Pair<BlockState, BlockPos>>,
        stack: MatrixStack,
        buffer: VertexConsumer,
        packedLight: Int,
        packedOverlay: Int,
        red: Float,
        green: Float,
        blue: Float
    ) {
        for (block in blocks) {
            renderBlockMatrix(block.first, block.second, stack, buffer, packedLight, packedOverlay, red, green, blue)
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
            renderBlockMatrices(entity!!.blocks, matrices, buffer, packedLight, packedOverlay, red, green, blue)
        }
    }

    companion object {
        fun getTexturedModelData(): TexturedModelData {
            return TexturedModelData.of(ModelData(), 0, 0)
        }
    }
}
