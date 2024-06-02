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
package org.stardustmodding.interstellar.forge

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.loading.FMLEnvironment
import org.jetbrains.annotations.ApiStatus
import org.stardustmodding.dynamicdimensions.impl.Constants
import org.stardustmodding.dynamicdimensions.impl.client.network.DynamicDimensionsS2CPacketReceivers.registerReceivers
import org.stardustmodding.dynamicdimensions.impl.command.DynamicDimensionsCommands.register
import org.stardustmodding.interstellar.forge.config.DynamicDimensionsConfigImpl

@ApiStatus.Internal
@Mod(Constants.MOD_ID)
class DynamicDimensionsForge {
    init {
        MinecraftForge.EVENT_BUS.addListener { event: RegisterCommandsEvent -> this.registerCommands(event) }
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DynamicDimensionsConfigImpl.SPEC)

        if (FMLEnvironment.dist.isClient) {
            registerReceivers()
        }
    }

    private fun registerCommands(event: RegisterCommandsEvent) {
        register(event.dispatcher, event.buildContext, event.commandSelection)
    }
}