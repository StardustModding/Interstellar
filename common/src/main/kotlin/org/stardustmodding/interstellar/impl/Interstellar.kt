package org.stardustmodding.interstellar.impl

import dev.architectury.event.events.common.CommandRegistrationEvent
import dev.architectury.event.events.common.LifecycleEvent
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.PartitioningSerializer
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.minecraft.util.ActionResult
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
        AutoConfig.register(
            InterstellarConfig::class.java,
            PartitioningSerializer.wrap { definition, configClass ->
                Toml4jConfigSerializer(
                    definition,
                    configClass
                )
            }
        )

        val holder = AutoConfig.getConfigHolder(InterstellarConfig::class.java)

        holder.registerSaveListener { _, config ->
            for (planet in config.planets.planets) {
                for (gas in planet.gases) {
                    if (!InterstellarRegistries.GASES.containsId(Identifier.tryParse(gas.id))) {
                        LOGGER.error("Cannot find unknown gas ${gas.id} for planet ${planet.name}!")
                    }
                }
            }

            ActionResult.SUCCESS
        }

        config = holder.config

        LifecycleEvent.SERVER_STARTING.register(Initializer::init)

        CommandRegistrationEvent.EVENT.register { dispatcher, _, _ ->
            for (cmd in commands) {
                cmd.register(dispatcher)
            }
        }
    }
}