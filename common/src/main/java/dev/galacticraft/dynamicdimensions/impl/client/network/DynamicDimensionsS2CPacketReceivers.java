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

package dev.galacticraft.dynamicdimensions.impl.client.network;

import dev.galacticraft.dynamicdimensions.impl.Constants;
import dev.galacticraft.dynamicdimensions.impl.registry.RegistryUtil;
import lol.bai.badpackets.api.S2CPacketReceiver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

public final class DynamicDimensionsS2CPacketReceivers {
    public static void registerReceivers() {
        S2CPacketReceiver.register(Constants.CREATE_WORLD_PACKET, (client, handler, buf, responseSender) -> createDynamicWorld(client, handler, buf));
        S2CPacketReceiver.register(Constants.DELETE_WORLD_PACKET, (client, handler, buf, responseSender) -> deleteDynamicWorld(client, handler, buf));
    }

    private static void createDynamicWorld(@NotNull MinecraftClient client, @NotNull ClientPlayNetworkHandler handler, @NotNull PacketByteBuf buf) {
        Identifier id = buf.readIdentifier();
        int rawId = buf.readInt();
        DimensionType type = DimensionType.CODEC.decode(NbtOps.INSTANCE, buf.readNbt()).get().orThrow().getFirst();
        client.execute(() -> {
            RegistryUtil.registerUnfreezeExact(handler.getRegistryManager().get(RegistryKeys.DIMENSION_TYPE), rawId, id, type);
            handler.getWorldKeys().add(RegistryKey.of(RegistryKeys.WORLD, id));
        });
    }

    private static void deleteDynamicWorld(@NotNull MinecraftClient client, @NotNull ClientPlayNetworkHandler handler, @NotNull PacketByteBuf buf) {
        Identifier id = buf.readIdentifier();
        client.execute(() -> {
            RegistryUtil.unregister(handler.getRegistryManager().get(RegistryKeys.DIMENSION_TYPE), id);
            handler.getWorldKeys().remove(RegistryKey.of(RegistryKeys.WORLD, id));
        });
    }

}
