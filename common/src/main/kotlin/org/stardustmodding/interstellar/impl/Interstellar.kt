package org.stardustmodding.interstellar.impl

import dev.architectury.event.events.common.CommandRegistrationEvent
import dev.architectury.event.events.common.LifecycleEvent
import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.PartitioningSerializer
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory
import org.stardustmodding.interstellar.impl.command.DimensionTpCommand
import org.stardustmodding.interstellar.impl.config.InterstellarConfig
import org.stardustmodding.interstellar.impl.init.Initializer

object Interstellar {
    const val MOD_ID = "interstellar"
    val LOGGER = LoggerFactory.getLogger(MOD_ID)

    private val items = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM)

    // This isn't a real item!
    private val rocket = items.register(
        "rocket"
    ) { Item(Item.Settings()) }

    private val creativeTab =
        CreativeTabRegistry.create(
            Text.translatable(id("creative_tab").toTranslationKey())
        ) {
            ItemStack(
                rocket.get()
            )
        }

    var config: InterstellarConfig? = null

    private var commands = listOf(
        DimensionTpCommand()
    )

    @JvmStatic
    fun id(id: String): Identifier {
        return Identifier(MOD_ID, id)
    }

    fun getItems(): DeferredRegister<Item> {
        return items
    }

    fun getTab(): ItemGroup {
        return creativeTab
    }

    fun init() {
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
        items.register()

        LifecycleEvent.SERVER_STARTED.register(Initializer::init)

        CommandRegistrationEvent.EVENT.register { dispatcher, _, _ ->
            for (cmd in commands) {
                cmd.register(dispatcher)
            }
        }
    }
}