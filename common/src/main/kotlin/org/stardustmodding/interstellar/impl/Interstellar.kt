package org.stardustmodding.interstellar.impl

import dev.architectury.event.events.common.CommandRegistrationEvent
import dev.architectury.event.events.common.LifecycleEvent
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.PartitioningSerializer
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import org.stardustmodding.interstellar.api.registries.InterstellarRegistries
import org.stardustmodding.interstellar.impl.command.DimensionTpCommand
import org.stardustmodding.interstellar.impl.config.InterstellarConfig
import org.stardustmodding.interstellar.impl.init.Initializer

object Interstellar {
    const val MOD_ID = "interstellar"

    val LOGGER = LoggerFactory.getLogger(MOD_ID)!!
    var config: InterstellarConfig? = null

    private var commands = listOf(
        DimensionTpCommand()
    )

    @JvmStatic
    fun id(id: String): Identifier {
        return Identifier(MOD_ID, id)
    }

    fun init() {
        InterstellarRegistries.init(null)

        AutoConfig.register(
            InterstellarConfig::class.java,
            PartitioningSerializer.wrap<InterstellarConfig, ConfigData?> { definition: Config?, configClass: Class<ConfigData?>? ->
                Toml4jConfigSerializer(
                    definition,
                    configClass
                )
            }
        )

        config = AutoConfig.getConfigHolder(InterstellarConfig::class.java).config

        LifecycleEvent.SERVER_STARTING.register(Initializer::init)

        CommandRegistrationEvent.EVENT.register { dispatcher, _, _ ->
            for (cmd in commands) {
                cmd.register(dispatcher)
            }
        }
    }
}