package org.stardustmodding.quantumengine.block

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.recipe.RecipeType
import net.minecraft.util.math.BlockPos
import org.stardustmodding.ionengine.block.PoweredBlockEntity

open class MachineBlock(type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?) : PoweredBlockEntity(type, pos, state) {

}
