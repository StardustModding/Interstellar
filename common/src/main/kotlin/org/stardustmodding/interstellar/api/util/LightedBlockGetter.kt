package org.stardustmodding.interstellar.api.util

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.ColorResolver
import net.minecraft.world.level.Level
import net.minecraft.world.level.LightLayer
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.lighting.LevelLightEngine
import net.minecraft.world.level.material.FluidState

class LightedBlockGetter(private var level: Level) : BlockAndTintGetter {
    override fun getHeight(): Int = level.height
    override fun getMinBuildHeight(): Int = level.minBuildHeight
    override fun getBlockEntity(pos: BlockPos): BlockEntity? = level.getBlockEntity(pos)
    override fun getBlockState(pos: BlockPos): BlockState = level.getBlockState(pos)
    override fun getFluidState(pos: BlockPos): FluidState = level.getFluidState(pos)
    override fun getShade(direction: Direction, shade: Boolean): Float = level.getShade(direction, shade)
    override fun getBlockTint(blockPos: BlockPos, colorResolver: ColorResolver): Int = level.getBlockTint(blockPos, colorResolver)
    @Suppress("WRONG_NULLABILITY_FOR_JAVA_OVERRIDE")
    override fun getLightEngine(): LevelLightEngine? = null
    override fun getBrightness(lightType: LightLayer, blockPos: BlockPos): Int = 15
}
