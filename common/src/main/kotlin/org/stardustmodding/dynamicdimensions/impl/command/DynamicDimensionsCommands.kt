/*
 * Copyright (c) 2021-2023 Team Galacticraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.stardustmodding.dynamicdimensions.impl.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.DimensionArgumentType
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.command.argument.NbtCompoundArgumentType
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtOps
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryOps
import net.minecraft.registry.tag.BlockTags
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.world.dimension.DimensionType
import net.minecraft.world.dimension.DimensionTypes
import net.minecraft.world.gen.chunk.ChunkGenerator
import net.minecraft.world.gen.chunk.FlatChunkGenerator
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig
import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry
import org.stardustmodding.dynamicdimensions.impl.Constants
import java.util.*

object DynamicDimensionsCommands {
    private val CANNOT_CREATE = SimpleCommandExceptionType(Text.translatable("command.dynamicdimensions.create.error"))
    private val CANNOT_DELETE = SimpleCommandExceptionType(Text.translatable("command.dynamicdimensions.delete.error"))

    @JvmStatic
    fun register(
        dispatcher: CommandDispatcher<ServerCommandSource?>,
        ignoredRegistryAccess: CommandRegistryAccess?,
        ignoredEnvironment: CommandManager.RegistrationEnvironment?
    ) {
        if (Constants.CONFIG.enableCommands()) {
            dispatcher.register(CommandManager.literal("dyndim")
                .requires { s: ServerCommandSource -> s.hasPermissionLevel(Constants.CONFIG.commandPermissionLevel()) }
                .then(
                    CommandManager.literal("create")
                        .then(
                            CommandManager.argument<Identifier>("id", IdentifierArgumentType.identifier())
                                .then(
                                    CommandManager.argument<NbtCompound>(
                                        "chunk_generator",
                                        NbtCompoundArgumentType.nbtCompound()
                                    )
                                        .then(
                                            CommandManager.argument<NbtCompound>(
                                                "dimension_type",
                                                NbtCompoundArgumentType.nbtCompound()
                                            )
                                                .executes { ctx: CommandContext<ServerCommandSource> ->
                                                    val id = IdentifierArgumentType.getIdentifier(ctx, "id")
                                                    val ops =
                                                        RegistryOps.of(NbtOps.INSTANCE, ctx.source.registryManager)
                                                    val generator = ChunkGenerator.CODEC.decode(
                                                        ops,
                                                        NbtCompoundArgumentType.getNbtCompound(
                                                            ctx,
                                                            "chunk_generator"
                                                        )
                                                    ).get().orThrow().first
                                                    val type = DimensionType.CODEC.decode(
                                                        ops,
                                                        NbtCompoundArgumentType.getNbtCompound(
                                                            ctx,
                                                            "dimension_type"
                                                        )
                                                    ).get().orThrow().first
                                                    val from: DynamicDimensionRegistry =
                                                        DynamicDimensionRegistry.from(ctx.source.server)
                                                    if (from.anyDimensionExists(id)) {
                                                        throw CANNOT_CREATE.create()
                                                    }
                                                    if (!from.createDynamicDimension(id, generator, type)) {
                                                        throw CANNOT_CREATE.create()
                                                    }
                                                    1
                                                })
                                )
                                .executes { ctx: CommandContext<ServerCommandSource> ->
                                    val id = IdentifierArgumentType.getIdentifier(ctx, "id")
                                    val access = ctx.source.registryManager
                                    val generator: ChunkGenerator = FlatChunkGenerator(
                                        FlatChunkGeneratorConfig.getDefaultConfig(
                                            access.getWrapperOrThrow(
                                                RegistryKeys.BIOME
                                            ),
                                            access.getWrapperOrThrow(RegistryKeys.STRUCTURE_SET),
                                            access.getWrapperOrThrow(
                                                RegistryKeys.PLACED_FEATURE
                                            )
                                        )
                                    )
                                    val type = DimensionType(
                                        OptionalLong.empty(),
                                        true,
                                        false,
                                        false,
                                        true,
                                        1.0,
                                        true,
                                        false,
                                        -64,
                                        384,
                                        384,
                                        BlockTags.INFINIBURN_OVERWORLD,
                                        DimensionTypes.OVERWORLD_ID,
                                        0.0f,
                                        DimensionType.MonsterSettings(
                                            false,
                                            true,
                                            UniformIntProvider.create(0, 7),
                                            0
                                        )
                                    )
                                    val from: DynamicDimensionRegistry =
                                        DynamicDimensionRegistry.from(ctx.source.server)
                                    if (from.anyDimensionExists(id)) {
                                        throw CANNOT_CREATE.create()
                                    }
                                    if (!from.createDynamicDimension(id, generator, type)) {
                                        throw CANNOT_CREATE.create()
                                    }
                                    1
                                })
                )
                .then(
                    CommandManager.literal("load")
                        .then(
                            CommandManager.argument<Identifier>("id", IdentifierArgumentType.identifier())
                                .then(
                                    CommandManager.argument<NbtCompound>(
                                        "chunk_generator",
                                        NbtCompoundArgumentType.nbtCompound()
                                    )
                                        .then(
                                            CommandManager.argument<NbtCompound>(
                                                "dimension_type",
                                                NbtCompoundArgumentType.nbtCompound()
                                            )
                                                .executes { ctx: CommandContext<ServerCommandSource> ->
                                                    val id = IdentifierArgumentType.getIdentifier(ctx, "id")
                                                    val ops =
                                                        RegistryOps.of(NbtOps.INSTANCE, ctx.source.registryManager)
                                                    val generator = ChunkGenerator.CODEC.decode(
                                                        ops,
                                                        NbtCompoundArgumentType.getNbtCompound(
                                                            ctx,
                                                            "chunk_generator"
                                                        )
                                                    ).get().orThrow().first
                                                    val type = DimensionType.CODEC.decode(
                                                        ops,
                                                        NbtCompoundArgumentType.getNbtCompound(
                                                            ctx,
                                                            "dimension_type"
                                                        )
                                                    ).get().orThrow().first
                                                    val from: DynamicDimensionRegistry =
                                                        DynamicDimensionRegistry.from(ctx.source.server)
                                                    if (from.anyDimensionExists(id)) {
                                                        throw CANNOT_CREATE.create()
                                                    }
                                                    if (!from.loadDynamicDimension(id, generator, type)) {
                                                        throw CANNOT_CREATE.create()
                                                    }
                                                    1
                                                })
                                ).executes { ctx: CommandContext<ServerCommandSource> ->
                                    val id = IdentifierArgumentType.getIdentifier(ctx, "id")
                                    val access = ctx.source.registryManager
                                    val generator: ChunkGenerator = FlatChunkGenerator(
                                        FlatChunkGeneratorConfig.getDefaultConfig(
                                            access.getWrapperOrThrow(
                                                RegistryKeys.BIOME
                                            ),
                                            access.getWrapperOrThrow(RegistryKeys.STRUCTURE_SET),
                                            access.getWrapperOrThrow(
                                                RegistryKeys.PLACED_FEATURE
                                            )
                                        )
                                    )
                                    val type = DimensionType(
                                        OptionalLong.empty(),
                                        true,
                                        false,
                                        false,
                                        true,
                                        1.0,
                                        true,
                                        false,
                                        -64,
                                        384,
                                        384,
                                        BlockTags.INFINIBURN_OVERWORLD,
                                        DimensionTypes.OVERWORLD_ID,
                                        0.0f,
                                        DimensionType.MonsterSettings(
                                            false,
                                            true,
                                            UniformIntProvider.create(0, 7),
                                            0
                                        )
                                    )
                                    val from: DynamicDimensionRegistry =
                                        DynamicDimensionRegistry.from(ctx.source.server)
                                    if (from.anyDimensionExists(id)) {
                                        throw CANNOT_CREATE.create()
                                    }
                                    if (!from.loadDynamicDimension(id, generator, type)) {
                                        throw CANNOT_CREATE.create()
                                    }
                                    1
                                })
                )
                .then(
                    CommandManager.literal("remove")
                        .then(
                            CommandManager.argument<Identifier>("id", DimensionArgumentType.dimension())
                                .executes { ctx: CommandContext<ServerCommandSource> ->
                                    val levelToDelete = DimensionArgumentType.getDimensionArgument(ctx, "id")
                                    val key = levelToDelete.registryKey
                                    val id = key.value
                                    if (!(ctx.source.server as DynamicDimensionRegistry).canDeleteDimension(id)) {
                                        throw CANNOT_DELETE.create()
                                    }
                                    (ctx.source.server as DynamicDimensionRegistry).removeDynamicDimension(id) { server, player ->
                                        player?.sendMessage(
                                            Text.translatable(
                                                "command.dynamicdimensions.delete.removed",
                                                id.toString()
                                            ), true
                                        )
                                        var level: ServerWorld? = server!!.getWorld(player?.spawnPointDimension)
                                        if (level != null) {
                                            val pos: BlockPos = player?.spawnPointPosition!!
                                            player.teleport(
                                                level,
                                                pos.x + 0.5,
                                                pos.y.toDouble(),
                                                pos.z + 0.5,
                                                player.yaw,
                                                player.pitch
                                            )
                                        } else {
                                            level = server.overworld
                                            val levelData = level.levelProperties
                                            player?.teleport(
                                                level,
                                                levelData.spawnX + 0.5,
                                                levelData.spawnY.toDouble(),
                                                levelData.spawnZ + 0.5,
                                                player.yaw,
                                                player.pitch
                                            )
                                        }
                                        player?.setVelocity(0.0, 0.0, 0.0)
                                    }
                                    1
                                })
                )
            )
        }
    }
}
