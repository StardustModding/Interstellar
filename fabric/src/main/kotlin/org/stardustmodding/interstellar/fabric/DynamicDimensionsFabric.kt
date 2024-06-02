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
package org.stardustmodding.interstellar.fabric

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import org.jetbrains.annotations.ApiStatus
import org.stardustmodding.dynamicdimensions.api.event.DimensionAddedCallback
import org.stardustmodding.dynamicdimensions.api.event.DimensionRemovedCallback
import org.stardustmodding.dynamicdimensions.api.event.DynamicDimensionLoadCallback
import org.stardustmodding.dynamicdimensions.impl.Constants

@ApiStatus.Internal
class DynamicDimensionsFabric : ModInitializer {
    override fun onInitialize() {
        if (Constants.CONFIG.enableCommands()) {
            if (FabricLoader.getInstance().isModLoaded("fabric-command-api-v2")) {
                registerCommandCallback()
            } else {
                Constants.LOGGER.warn("Unable to register commands as the fabric command api module (fabric-command-api-v2) is not installed.")
            }
        }
    }

    companion object {
        @JvmField
        val DIMENSION_ADDED_EVENT: Event<DimensionAddedCallback?> =
            EventFactory.createArrayBacked<DimensionAddedCallback?>(
                DimensionAddedCallback::class.java
            ) { t: Array<DimensionAddedCallback?> ->
                DimensionAddedCallback { key, level ->
                    for (callback in t) {
                        callback?.dimensionAdded(key, level)
                    }
                }
            }

        @JvmField
        val DIMENSION_REMOVED_EVENT: Event<DimensionRemovedCallback?> =
            EventFactory.createArrayBacked<DimensionRemovedCallback?>(
                DimensionRemovedCallback::class.java
            ) { t: Array<DimensionRemovedCallback?> ->
                DimensionRemovedCallback { key, level ->
                    for (callback in t) {
                        callback?.dimensionRemoved(key, level)
                    }
                }
            }

        @JvmField
        val DIMENSION_LOAD_EVENT: Event<DynamicDimensionLoadCallback?> =
            EventFactory.createArrayBacked<DynamicDimensionLoadCallback?>(
                /* type = */ DynamicDimensionLoadCallback::class.java,
                /* invokerFactory = */ { t: Array<DynamicDimensionLoadCallback?> ->
                    DynamicDimensionLoadCallback { server, loader ->
                        for (callback in t) {
                            callback?.loadDimensions(server, loader)
                        }
                    }
                })

        private fun registerCommandCallback() {
            CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { obj: CommandDispatcher<ServerCommandSource>, dispatcher: CommandRegistryAccess, ignoredRegistryAccess: CommandManager.RegistrationEnvironment ->
                obj.register(
                    dispatcher,
                    ignoredRegistryAccess
                )
            })
        }
    }
}
