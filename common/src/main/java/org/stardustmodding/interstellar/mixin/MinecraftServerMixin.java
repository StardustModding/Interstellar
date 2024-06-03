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

package org.stardustmodding.interstellar.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.DataResult;
import org.stardustmodding.dynamicdimensions.api.DynamicDimensionRegistry;
import org.stardustmodding.dynamicdimensions.api.PlayerRemover;
import org.stardustmodding.dynamicdimensions.api.event.DimensionAddedCallback;
import org.stardustmodding.dynamicdimensions.api.event.DimensionRemovedCallback;
import org.stardustmodding.dynamicdimensions.api.event.DynamicDimensionLoadCallback;
import org.stardustmodding.dynamicdimensions.impl.accessor.PrimaryLevelDataAccessor;
import org.stardustmodding.dynamicdimensions.impl.registry.RegistryUtil;
import org.stardustmodding.dynamicdimensions.impl.Constants;
import io.netty.buffer.Unpooled;
import lol.bai.badpackets.api.PacketSender;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.SynchronizeTagsS2CPacket;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.registry.tag.TagManagerLoader;
import net.minecraft.registry.tag.TagPacketSerializer;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ApiServices;
import net.minecraft.util.Identifier;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.border.WorldBorderListener;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin implements DynamicDimensionRegistry {
    @Unique
    private final @NotNull Map<RegistryKey<World>, ServerWorld> levelsAwaitingCreation = new HashMap<>();
    @Unique
    private final @NotNull Map<RegistryKey<World>, PlayerRemover> levelsAwaitingDeletion = new HashMap<>();
    @Unique
    private final @NotNull List<RegistryKey<World>> dynamicDimensions = new ArrayList<>();
    @Unique
    private boolean tickingLevels = false;

    @Shadow
    private MinecraftServer.ResourceManagerHolder resourceManagerHolder;
    @Shadow
    @Final
    private Executor workerExecutor;
    @Shadow
    @Final
    private WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory;
    @Shadow
    @Final
    private Map<RegistryKey<World>, ServerWorld> worlds;

    @Shadow
    public abstract ServerWorld getOverworld();

    @Shadow
    public abstract PlayerManager getPlayerManager();

    @Shadow
    public abstract DynamicRegistryManager.Immutable getRegistryManager();

    @Shadow
    public abstract SaveProperties getSaveProperties();

    @Shadow
    public abstract CombinedDynamicRegistries<ServerDynamicRegistryType> getCombinedDynamicRegistries();

    @Shadow @Final protected LevelStorage.Session session;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initDynamicDimensions(Thread thread, LevelStorage.Session session, ResourcePackManager dataPackManager, SaveLoader saveLoader, Proxy proxy, DataFixer dataFixer, ApiServices apiServices, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory, CallbackInfo ci) {
        ((PrimaryLevelDataAccessor) saveLoader.saveProperties()).setDynamicList(this.dynamicDimensions);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;tickWorlds(Ljava/util/function/BooleanSupplier;)V", shift = At.Shift.BEFORE))
    private void addLevels(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (!this.levelsAwaitingCreation.isEmpty()) {
            for (Map.Entry<RegistryKey<World>, ServerWorld> entry : this.levelsAwaitingCreation.entrySet()) {
                this.registerLevel(entry.getKey(), entry.getValue());
            }
            this.levelsAwaitingCreation.clear();
        }

        if (!this.levelsAwaitingDeletion.isEmpty()) {
            for (Map.Entry<RegistryKey<World>, PlayerRemover> entry : this.levelsAwaitingDeletion.entrySet()) {
                this.deleteLevel(entry.getKey(), entry.getValue());
            }
            this.levelsAwaitingDeletion.clear();
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Unique
    private void deleteLevel(RegistryKey<World> key, PlayerRemover playerRemover) {
        try (ServerWorld world = this.worlds.remove(key)) {
            DimensionRemovedCallback.invoke(key, world);

            List<ServerPlayerEntity> players = new ArrayList<>(world.getPlayers()); // prevent co-modification

            for (var player : players) {
                playerRemover.removePlayer((MinecraftServer) (Object) this, player);
            }

            world.save(null, true, false);
        } catch (IOException e) {
            Constants.LOGGER.error("Failed to close level upon removal! Memory may have been leaked.", e);
        }

        this.deleteLevelData(key);

        RegistryUtil.unregister(this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.WORLD), key.getValue());
        RegistryUtil.unregister(this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION_TYPE), key.getValue());
        this.dynamicDimensions.remove(key);

        PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
        packetByteBuf.writeIdentifier(key.getValue());
        this.getPlayerManager().sendToAll(new CustomPayloadS2CPacket(Constants.DELETE_WORLD_PACKET, packetByteBuf));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Unique
    private void deleteLevelData(RegistryKey<World> key) {
        Path worldDir = this.session.getWorldDirectory(key);
        if (worldDir.toFile().exists()) {
            if (Constants.CONFIG.deleteRemovedDimensions()) {
                try {
                    FileUtils.deleteDirectory(worldDir.toFile());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to delete deleted world directory!", e);
                }
            } else {
                try {
                    Path resolved;
                    String id = key.getValue().toString().replace(":", ",");
                    if (worldDir.getParent().getFileName().toString().equals(key.getValue().getNamespace())) {
                        resolved = worldDir.getParent().resolveSibling("deleted").resolve(id);
                    } else {
                        resolved = worldDir.resolveSibling(id + "_deleted");
                    }
                    if (resolved.toFile().exists()) {
                        FileUtils.deleteDirectory(resolved.toFile());
                    }
                    resolved.toFile().mkdirs();
                    Files.move(worldDir, resolved, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    Constants.LOGGER.error("Failed to move removed dimension's directory.", e);
                    try {
                        FileUtils.deleteDirectory(worldDir.toFile());
                    } catch (IOException ex) {
                        ex.addSuppressed(e);
                        throw new RuntimeException("Failed to delete removed dimension's directory!", ex); // TODO: is a throw necessary here? what happens if a world is re-created with the same id? is it exploitable?
                    }
                }
            }
        }
    }

    @Unique
    private void registerLevel(RegistryKey<World> key, ServerWorld level) {
        DimensionAddedCallback.invoke(key, level);
        this.worlds.put(key, level);
        this.dynamicDimensions.add(key);
        level.tick(() -> true);
    }

    @Inject(method = "tickWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/function/CommandFunctionManager;tick()V", shift = At.Shift.AFTER))
    private void markTickingLevels(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        this.tickingLevels = true;
    }

    @Inject(method = "tickWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/ServerNetworkIo;tick()V", shift = At.Shift.BEFORE))
    private void markNotTickingLevels(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        this.tickingLevels = false;
    }

    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void loadDynamicDimensions(CallbackInfo ci) {
        final Registry<DimensionType> typeRegistry = this.getRegistryManager().get(RegistryKeys.DIMENSION_TYPE);
        final Registry<DimensionOptions> stemRegistry = this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION);

        DynamicDimensionLoadCallback.invoke((MinecraftServer) (Object) this, (id, chunkGenerator, type) -> {
            Constants.LOGGER.debug("Loading dynamic dimension '{}'", id);
            RegistryEntry.Reference<DimensionType> ref = RegistryUtil.registerUnfreeze(typeRegistry, id, type);
            RegistryUtil.registerUnfreeze(stemRegistry, id, new DimensionOptions(ref, chunkGenerator));
            this.dynamicDimensions.add(RegistryKey.of(RegistryKeys.WORLD, id));
        });
    }

    @Override
    public boolean createDynamicDimension(@NotNull Identifier id, @NotNull ChunkGenerator generator, @NotNull DimensionType type) {
        RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, id);
        if (!this.canCreateDimension(id) || this.levelsAwaitingCreation.containsKey(key)) return false;
        final Registry<DimensionType> typeRegistry = this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION_TYPE);
        final Registry<DimensionOptions> stemRegistry = this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION);
        Constants.LOGGER.debug("Attempting to create dynamic dimension '{}'", id);

        if (typeRegistry.stream().anyMatch(t -> t == type)) {
            return false;
        }

        final DataResult<NbtElement> encodedType = DimensionType.CODEC.encode(type, NbtOps.INSTANCE, new NbtCompound());
        if (encodedType.error().isPresent()) {
            Constants.LOGGER.error("Failed to encode dimension type! {}", encodedType.error().get().message());
            return false;
        }

        final NbtCompound serializedType = (NbtCompound) encodedType.get().orThrow();

        this.createDynamicWorld(id, generator, type, typeRegistry, stemRegistry, serializedType, key, true);
        return true;
    }

    @Override
    public boolean loadDynamicDimension(@NotNull Identifier id, @NotNull ChunkGenerator generator, @NotNull DimensionType type) {
        RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, id);
        if (!this.canCreateDimension(id) || this.levelsAwaitingCreation.containsKey(key)) return false;
        final Registry<DimensionType> typeRegistry = this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION_TYPE);
        final Registry<DimensionOptions> stemRegistry = this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION);
        Constants.LOGGER.debug("Attempting to create dynamic dimension '{}'", id);

        if (typeRegistry.stream().anyMatch(t -> t == type)) {
            return false;
        }

        final DataResult<NbtElement> encodedType = DimensionType.CODEC.encode(type, NbtOps.INSTANCE, new NbtCompound());
        if (encodedType.error().isPresent()) {
            Constants.LOGGER.error("Failed to encode dimension type! {}", encodedType.error().get().message());
            return false;
        }

        final NbtCompound serializedType = (NbtCompound) encodedType.get().orThrow();

        this.createDynamicWorld(id, generator, type, typeRegistry, stemRegistry, serializedType, key, false);
        return true;
    }

    @Unique
    private void createDynamicWorld(@NotNull Identifier id, @NotNull ChunkGenerator generator, @NotNull DimensionType type, Registry<DimensionType> typeRegistry, Registry<DimensionOptions> stemRegistry, NbtCompound serializedType, RegistryKey<World> key, boolean deleteOldData) {
        final SaveProperties worldData = this.getSaveProperties();
        final ServerWorld overworld = this.getOverworld();
        assert overworld != null;

        final RegistryEntry.Reference<DimensionType> typeHolder = RegistryUtil.registerUnfreeze(typeRegistry, id, type);
        assert typeHolder.hasKeyAndValue() : "Registered dimension type not bound?!";

        final DimensionOptions stem = new DimensionOptions(typeHolder, generator);
        RegistryUtil.registerUnfreeze(stemRegistry, id, stem);

        final UnmodifiableLevelProperties data = new UnmodifiableLevelProperties(worldData, worldData.getMainWorldProperties()); //todo: do we want separate data?
        if (deleteOldData) {
            this.deleteLevelData(key);
        }
        final ServerWorld level = new ServerWorld(
                (MinecraftServer) (Object) this,
                this.workerExecutor,
                this.session,
                data,
                key,
                stem,
                this.worldGenerationProgressListenerFactory.create(10),
                worldData.isDebugWorld(),
                BiomeAccess.hashSeed(worldData.getGeneratorOptions().getSeed()),
                ImmutableList.of(),
                false,
                null
        );
        overworld.getWorldBorder().addListener(new WorldBorderListener.WorldBorderSyncer(level.getWorldBorder()));
        level.getChunkManager().applySimulationDistance(((DistanceManagerAccessor) ((ServerChunkCacheAccessor) overworld.getChunkManager()).getDistanceManager()).getSimulationDistance());
        level.getChunkManager().applyViewDistance(((ChunkMapAccessor) overworld.getChunkManager().threadedAnvilChunkStorage).getViewDistance());

        if (this.tickingLevels) {
            this.levelsAwaitingCreation.put(key, level); //prevent co-modification
        } else {
            this.registerLevel(key, level);
        }

        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIdentifier(id);
        buf.writeInt(typeRegistry.getRawId(type));
        buf.writeNbt(serializedType);
        for (ServerPlayerEntity player : this.getPlayerManager().getPlayerList()) {
            PacketSender.s2c(player).send(Constants.CREATE_WORLD_PACKET, new PacketByteBuf(buf.copy()));
        }
        this.reloadTags();
    }

    @Override
    public boolean dynamicDimensionExists(@NotNull Identifier id) {
        return this.dynamicDimensions.contains(RegistryKey.of(RegistryKeys.DIMENSION, id));
    }

    @Override
    public boolean anyDimensionExists(@NotNull Identifier id) {
        return this.worlds.containsKey(RegistryKey.of(RegistryKeys.WORLD, id)) || this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION_TYPE).containsId(id) || this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.WORLD).containsId(id);
    }

    @Unique
    private boolean canCreateDimensions() {
        return Constants.CONFIG.allowDimensionCreation();
    }

    @Override
    public boolean canDeleteDimension(@NotNull Identifier id) {
        return this.dynamicDimensionExists(id) && (Constants.CONFIG.deleteDimensionsWithPlayers() || this.worlds.get(RegistryKey.of(RegistryKeys.WORLD, id)).getPlayers().isEmpty());
    }

    @Override
    public boolean canCreateDimension(@NotNull Identifier id) {
        return !this.anyDimensionExists(id) && !this.dynamicDimensionExists(id) && this.canCreateDimensions();
    }

    @Override
    public boolean removeDynamicDimension(@NotNull Identifier id, @NotNull PlayerRemover remover) {
        RegistryKey<World> key = RegistryKey.of(RegistryKeys.WORLD, id);
        if (!this.canDeleteDimension(id) || this.levelsAwaitingDeletion.containsKey(key)) return false;

        if (this.tickingLevels) {
            this.levelsAwaitingDeletion.put(key, remover);
        } else {
            this.deleteLevel(key, remover);
        }

        return true;
    }

    @Unique
    private void reloadTags() {
        for (TagManagerLoader.RegistryTags<?> result : ((ReloadableServerResourcesAccessor) this.resourceManagerHolder.dataPackContents()).getTagManager().getRegistryTags()) {
            if (result.key() == RegistryKeys.DIMENSION_TYPE) {
                Registry<DimensionType> types = this.getCombinedDynamicRegistries().getCombinedRegistryManager().get(RegistryKeys.DIMENSION_TYPE);
                types.clearTags();
                //noinspection unchecked - we know that the registry is a registry of dimension types as the key is correct
                types.populateTags(((TagManagerLoader.RegistryTags<DimensionType>) result).tags().entrySet()
                        .stream()
                        .collect(Collectors.toUnmodifiableMap(entry -> TagKey.of(RegistryKeys.DIMENSION_TYPE, entry.getKey()), entry -> entry.getValue().stream().toList())));
                break;
            }
        }
        this.getPlayerManager().sendToAll(new SynchronizeTagsS2CPacket(TagPacketSerializer.serializeTags(this.getCombinedDynamicRegistries())));
    }
}
