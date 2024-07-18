package org.stardustmodding.quantumengine.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import org.stardustmodding.ionengine.block.PoweredBlockEntity

open class MachineBlock(type: BlockEntityType<*>, pos: BlockPos, state: BlockState) : PoweredBlockEntity(type, pos, state) {

}
