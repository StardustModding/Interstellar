package org.stardustmodding.skyengine.multipart

import net.minecraft.world.entity.EntityType
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

abstract class BlockAggregateEntity(type: EntityType<*>, world: Level) : MultipartEntity(type, world) {
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
