package org.stardustmodding.dynamicdimensions.forge

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
import org.stardustmodding.dynamicdimensions.forge.config.DynamicDimensionsConfigImpl

@Suppress("unused")
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