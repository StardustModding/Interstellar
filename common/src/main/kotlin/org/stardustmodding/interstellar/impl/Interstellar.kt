package org.stardustmodding.interstellar.impl

import dev.architectury.event.events.common.CommandRegistrationEvent
import dev.architectury.event.events.common.TickEvent
import dev.architectury.registry.ReloadListenerRegistry
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.PartitioningSerializer
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.PackType
import org.slf4j.LoggerFactory
import org.stardustmodding.interstellar.api.registry.InterstellarRegistries
import org.stardustmodding.interstellar.impl.command.DimensionTpCommand
import org.stardustmodding.interstellar.impl.command.OpenScreenCommand
import org.stardustmodding.interstellar.impl.config.InterstellarConfig
import org.stardustmodding.interstellar.impl.init.Entities
import org.stardustmodding.interstellar.impl.init.Screens
import org.stardustmodding.interstellar.impl.resource.ReloadListener

object Interstellar {
    const val MOD_ID = "interstellar"

    @JvmField
    val LOGGER = LoggerFactory.getLogger(MOD_ID)!!

    @JvmField
    var CONFIG: InterstellarConfig? = null

    private var commands = listOf(
        DimensionTpCommand(),
        OpenScreenCommand()
    )

    @JvmStatic
    fun id(id: String): ResourceLocation {
        return ResourceLocation(MOD_ID, id)
    }

    fun init() {
        AutoConfig.register(
            InterstellarConfig::class.java,
            PartitioningSerializer.wrap { definition, configClass ->
                Toml4jConfigSerializer(
                    definition,
                    configClass
                )
            }
        )

        CONFIG = AutoConfig.getConfigHolder(InterstellarConfig::class.java).config

        eagerInit()

        ReloadListenerRegistry.register(PackType.SERVER_DATA, ReloadListener)

        TickEvent.SERVER_LEVEL_POST.register {
            for (planet in InterstellarRegistries.PLANETS) {
                planet.tick(it)
            }
        }

        CommandRegistrationEvent.EVENT.register { dispatcher, _, _ ->
            for (cmd in commands) {
                cmd.register(dispatcher)
            }
        }
    }

    private fun eagerInit() {
        Entities
        Screens
    }
}
