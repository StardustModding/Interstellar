package org.stardustmodding.interstellar.api.builder

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.world.biome.source.util.MultiNoiseUtil.NoiseHypercube
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings
import net.minecraft.world.gen.chunk.GenerationShapeConfig
import net.minecraft.world.gen.noise.NoiseRouter
import net.minecraft.world.gen.surfacebuilder.MaterialRules

class ChunkGeneratorSettingsBuilder {

    private var generationShape = GenerationShapeConfig.create(-64, 128, 1, 2)
    private var defaultBlock = Blocks.STONE.defaultState
    private var defaultFluid = Blocks.AIR.defaultState
    private var noiseRouter = NoiseRouterBuilder().build()
    private var surfaceRule = MaterialRules.sequence()
    private var spawnTarget: MutableList<NoiseHypercube> = mutableListOf()
    private var seaLevel = 0
    private var disableMobGeneration = false
    private var aquifersEnabled = false
    private var oreVeinsEnabled = true
    private var legacyRandomSource = false

    fun generationShape(value: GenerationShapeConfig) = apply { this.generationShape = value }
    fun defaultBlock(value: BlockState) = apply { this.defaultBlock = value }
    fun defaultFluid(value: BlockState) = apply { this.defaultFluid = value }
    fun noiseRouter(value: NoiseRouter) = apply { this.noiseRouter = value }
    fun surfaceRule(value: MaterialRules.MaterialRule) = apply { this.surfaceRule = value }
    fun spawnTarget(value: MutableList<NoiseHypercube>) = apply { this.spawnTarget = value }
    fun seaLevel(value: Int) = apply { this.seaLevel = value }
    fun disableMobGeneration(value: Boolean) = apply { this.disableMobGeneration = value }
    fun aquifersEnabled(value: Boolean) = apply { this.aquifersEnabled = value }
    fun oreVeinsEnabled(value: Boolean) = apply { this.oreVeinsEnabled = value }
    fun legacyRandomSource(value: Boolean) = apply { this.legacyRandomSource = value }

    fun build() = ChunkGeneratorSettings(
        generationShape,
        defaultBlock,
        defaultFluid,
        noiseRouter,
        surfaceRule,
        spawnTarget,
        seaLevel,
        disableMobGeneration,
        aquifersEnabled,
        oreVeinsEnabled,
        legacyRandomSource,
    )
}
