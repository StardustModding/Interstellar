package org.stardustmodding.skyengine.multipart

import net.minecraft.block.BlockState
import net.minecraft.entity.EntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class BlockAggregateEntity(type: EntityType<*>?, world: World?) : MultipartEntity(type, world) {
    private val blocks = mutableMapOf<BlockPos, BlockState>()

    init {
        buildParts()
    }

    private fun buildParts() {
        blocks.forEach { (pos, state) ->
            val part = BlockEntityPart(this, pos, state)

            addPart(part)
        }
    }
}
