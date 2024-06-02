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
package org.stardustmodding.dynamicdimensions.impl.client.network

import lol.bai.badpackets.api.PacketSender
import lol.bai.badpackets.api.S2CPacketReceiver
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.nbt.NbtOps
import net.minecraft.network.PacketByteBuf
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.world.dimension.DimensionType
import org.stardustmodding.dynamicdimensions.impl.Constants
import org.stardustmodding.dynamicdimensions.impl.registry.RegistryUtil

object DynamicDimensionsS2CPacketReceivers {
    @JvmStatic
    fun registerReceivers() {
        S2CPacketReceiver.register(Constants.CREATE_WORLD_PACKET) { client: MinecraftClient, handler: ClientPlayNetworkHandler, buf: PacketByteBuf, responseSender: PacketSender? ->
            createDynamicWorld(
                client,
                handler,
                buf
            )
        }
        S2CPacketReceiver.register(Constants.DELETE_WORLD_PACKET) { client: MinecraftClient, handler: ClientPlayNetworkHandler, buf: PacketByteBuf, responseSender: PacketSender? ->
            deleteDynamicWorld(
                client,
                handler,
                buf
            )
        }
    }

    private fun createDynamicWorld(client: MinecraftClient, handler: ClientPlayNetworkHandler, buf: PacketByteBuf) {
        val id = buf.readIdentifier()
        val rawId = buf.readInt()
        val type = DimensionType.CODEC.decode(NbtOps.INSTANCE, buf.readNbt()).get().orThrow().first
        client.execute {
            RegistryUtil.registerUnfreezeExact(
                handler.registryManager.get(RegistryKeys.DIMENSION_TYPE),
                rawId,
                id,
                type
            )
            handler.worldKeys.add(RegistryKey.of(RegistryKeys.WORLD, id))
        }
    }

    private fun deleteDynamicWorld(client: MinecraftClient, handler: ClientPlayNetworkHandler, buf: PacketByteBuf) {
        val id = buf.readIdentifier()
        client.execute {
            RegistryUtil.unregister(handler.registryManager.get(RegistryKeys.DIMENSION_TYPE), id)
            handler.worldKeys.remove(RegistryKey.of(RegistryKeys.WORLD, id))
        }
    }
}
