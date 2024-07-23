package org.stardustmodding.skyengine.managed

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.lighting.LevelLightEngine
import net.minecraft.world.level.material.FluidState

class ManagedBlockGetter(
    /**
     * A reference to the `skyengine:managed` level.
     */
    private var level: Level,
    /**
     * The offset to where the actual blocks are located. (Determined by index)
     */
    private var offset: ChunkPos
) : BlockAndTintGetter {
    private fun getRealPos(pos: BlockPos): BlockPos = pos.offset(offset.x, 0, offset.z)

    override fun getHeight(): Int = level.height
    override fun getMinBuildHeight(): Int = level.minBuildHeight
    override fun getBlockEntity(pos: BlockPos): BlockEntity? = level.getBlockEntity(getRealPos(pos))
    override fun getBlockState(pos: BlockPos): BlockState = level.getBlockState(getRealPos(pos))
    override fun getFluidState(pos: BlockPos): FluidState = level.getFluidState(getRealPos(pos))
    override fun getShade(direction: Direction, shade: Boolean): Float = level.getShade(direction, shade)
    override fun getBlockTint(blockPos: BlockPos, colorResolver: ColorResolver): Int = level.getBlockTint(getRealPos(blockPos), colorResolver)
    // @Suppress("WRONG_NULLABILITY_FOR_JAVA_OVERRIDE")
    // override fun getLightEngine(): LevelLightEngine? = null
    override fun getLightEngine(): LevelLightEngine = level.lightEngine
    override fun getBrightness(lightType: LightLayer, blockPos: BlockPos): Int = level.getBrightness(lightType, getRealPos(blockPos))
}
