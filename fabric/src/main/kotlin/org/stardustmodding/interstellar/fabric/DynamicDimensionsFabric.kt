package org.stardustmodding.interstellar.fabric

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.fabricmc.loader.api.FabricLoader
import org.jetbrains.annotations.ApiStatus
import org.stardustmodding.dynamicdimensions.api.event.DimensionAddedCallback
import org.stardustmodding.dynamicdimensions.api.event.DimensionRemovedCallback
import org.stardustmodding.dynamicdimensions.api.event.DynamicDimensionLoadCallback
import org.stardustmodding.dynamicdimensions.impl.Constants
import org.stardustmodding.dynamicdimensions.impl.command.DynamicDimensionsCommands

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
                /* type = */ DynamicDimensionLoadCallback::class.java
            )
            { t: Array<DynamicDimensionLoadCallback?> ->
                DynamicDimensionLoadCallback { server, loader ->
                    for (callback in t) {
                        callback?.loadDimensions(server, loader)
                    }
                }
            }

        private fun registerCommandCallback() {
            CommandRegistrationCallback.EVENT.register(DynamicDimensionsCommands::register)
        }
    }
}
