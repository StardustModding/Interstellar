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

package dev.galacticraft.dynamicdimensions.impl.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dev.galacticraft.dynamicdimensions.api.DynamicDimensionRegistry;
import dev.galacticraft.dynamicdimensions.impl.Constants;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.NbtCompoundArgumentType;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalLong;

public final class DynamicDimensionsCommands {
    private static final SimpleCommandExceptionType CANNOT_CREATE = new SimpleCommandExceptionType(Text.translatable("command.dynamicdimensions.create.error"));
    private static final SimpleCommandExceptionType CANNOT_DELETE = new SimpleCommandExceptionType(Text.translatable("command.dynamicdimensions.delete.error"));

    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess ignoredRegistryAccess, CommandManager.RegistrationEnvironment ignoredEnvironment) {
        if (Constants.CONFIG.enableCommands()) {
            dispatcher.register(CommandManager.literal("dyndim")
                    .requires(s -> s.hasPermissionLevel(Constants.CONFIG.commandPermissionLevel()))
                    .then(CommandManager.literal("create")
                            .then(CommandManager.argument("id", IdentifierArgumentType.identifier())
                                    .then(CommandManager.argument("chunk_generator", NbtCompoundArgumentType.nbtCompound())
                                            .then(CommandManager.argument("dimension_type", NbtCompoundArgumentType.nbtCompound())
                                                    .executes(ctx -> {
                                                        Identifier id = IdentifierArgumentType.getIdentifier(ctx, "id");
                                                        RegistryOps<NbtElement> ops = RegistryOps.of(NbtOps.INSTANCE, ctx.getSource().getRegistryManager());
                                                        ChunkGenerator generator = ChunkGenerator.CODEC.decode(ops, NbtCompoundArgumentType.getNbtCompound(ctx, "chunk_generator")).get().orThrow().getFirst();
                                                        DimensionType type = DimensionType.CODEC.decode(ops, NbtCompoundArgumentType.getNbtCompound(ctx, "dimension_type")).get().orThrow().getFirst();
                                                        DynamicDimensionRegistry from = DynamicDimensionRegistry.from(ctx.getSource().getServer());
                                                        if (from.anyDimensionExists(id)) {
                                                            throw CANNOT_CREATE.create();
                                                        }
                                                        if (!from.createDynamicDimension(id, generator, type)) {
                                                            throw CANNOT_CREATE.create();
                                                        }
                                                        return 1;
                                                    })))
                                    .executes(ctx -> {
                                        Identifier id = IdentifierArgumentType.getIdentifier(ctx, "id");
                                        DynamicRegistryManager access = ctx.getSource().getRegistryManager();
                                        ChunkGenerator generator = new FlatChunkGenerator(FlatChunkGeneratorConfig.getDefaultConfig(access.getWrapperOrThrow(RegistryKeys.BIOME), access.getWrapperOrThrow(RegistryKeys.STRUCTURE_SET), access.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE)));
                                        DimensionType type = new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0D, true, false, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, DimensionTypes.OVERWORLD_ID, 0.0F, new DimensionType.MonsterSettings(false, true, UniformIntProvider.create(0, 7), 0));
                                        DynamicDimensionRegistry from = DynamicDimensionRegistry.from(ctx.getSource().getServer());
                                        if (from.anyDimensionExists(id)) {
                                            throw CANNOT_CREATE.create();
                                        }
                                        if (!from.createDynamicDimension(id, generator, type)) {
                                            throw CANNOT_CREATE.create();
                                        }
                                        return 1;
                                    })))
                    .then(CommandManager.literal("load")
                            .then(CommandManager.argument("id", IdentifierArgumentType.identifier())
                                    .then(CommandManager.argument("chunk_generator", NbtCompoundArgumentType.nbtCompound())
                                            .then(CommandManager.argument("dimension_type", NbtCompoundArgumentType.nbtCompound())
                                                    .executes(ctx -> {
                                                        Identifier id = IdentifierArgumentType.getIdentifier(ctx, "id");
                                                        RegistryOps<NbtElement> ops = RegistryOps.of(NbtOps.INSTANCE, ctx.getSource().getRegistryManager());
                                                        ChunkGenerator generator = ChunkGenerator.CODEC.decode(ops, NbtCompoundArgumentType.getNbtCompound(ctx, "chunk_generator")).get().orThrow().getFirst();
                                                        DimensionType type = DimensionType.CODEC.decode(ops, NbtCompoundArgumentType.getNbtCompound(ctx, "dimension_type")).get().orThrow().getFirst();
                                                        DynamicDimensionRegistry from = DynamicDimensionRegistry.from(ctx.getSource().getServer());
                                                        if (from.anyDimensionExists(id)) {
                                                            throw CANNOT_CREATE.create();
                                                        }
                                                        if (!from.loadDynamicDimension(id, generator, type)) {
                                                            throw CANNOT_CREATE.create();
                                                        }
                                                        return 1;
                                                    }))).executes(ctx -> {
                                        Identifier id = IdentifierArgumentType.getIdentifier(ctx, "id");
                                        DynamicRegistryManager access = ctx.getSource().getRegistryManager();
                                        ChunkGenerator generator = new FlatChunkGenerator(FlatChunkGeneratorConfig.getDefaultConfig(access.getWrapperOrThrow(RegistryKeys.BIOME), access.getWrapperOrThrow(RegistryKeys.STRUCTURE_SET), access.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE)));
                                        DimensionType type = new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0D, true, false, -64, 384, 384, BlockTags.INFINIBURN_OVERWORLD, DimensionTypes.OVERWORLD_ID, 0.0F, new DimensionType.MonsterSettings(false, true, UniformIntProvider.create(0, 7), 0));
                                        DynamicDimensionRegistry from = DynamicDimensionRegistry.from(ctx.getSource().getServer());
                                        if (from.anyDimensionExists(id)) {
                                            throw CANNOT_CREATE.create();
                                        }
                                        if (!from.loadDynamicDimension(id, generator, type)) {
                                            throw CANNOT_CREATE.create();
                                        }
                                        return 1;
                                    })))
                    .then(CommandManager.literal("remove")
                            .then(CommandManager.argument("id", DimensionArgumentType.dimension())
                                    .executes(ctx -> {
                                        ServerWorld levelToDelete = DimensionArgumentType.getDimensionArgument(ctx, "id");
                                        RegistryKey<World> key = levelToDelete.getRegistryKey();
                                        Identifier id = key.getValue();
                                        if (!((DynamicDimensionRegistry) ctx.getSource().getServer()).canDeleteDimension(id)) {
                                            throw CANNOT_DELETE.create();
                                        }
                                        ((DynamicDimensionRegistry) ctx.getSource().getServer()).removeDynamicDimension(id, (server, player) -> {
                                            player.sendMessage(Text.translatable("command.dynamicdimensions.delete.removed", id.toString()), true);
                                            ServerWorld level = server.getWorld(player.getSpawnPointDimension());
                                            if (level != null) {
                                                BlockPos pos = player.getSpawnPointPosition();
                                                if (pos != null) {
                                                    player.teleport(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, player.getYaw(), player.getPitch());
                                                } else {
                                                    WorldProperties levelData = level.getLevelProperties();
                                                    player.teleport(level, levelData.getSpawnX() + 0.5, levelData.getSpawnY(), levelData.getSpawnZ() + 0.5, player.getYaw(), player.getPitch());
                                                }
                                            } else {
                                                level = server.getOverworld();
                                                WorldProperties levelData = level.getLevelProperties();
                                                player.teleport(level, levelData.getSpawnX() + 0.5, levelData.getSpawnY(), levelData.getSpawnZ() + 0.5, player.getYaw(), player.getPitch());
                                            }
                                            player.setVelocity(0.0, 0.0, 0.0);
                                        });
                                        return 1;
                                    }))));
        }
    }
}
