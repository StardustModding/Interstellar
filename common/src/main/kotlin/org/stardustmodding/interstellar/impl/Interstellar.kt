package org.stardustmodding.interstellar.impl

import com.mojang.brigadier.CommandDispatcher
import dev.architectury.event.events.common.CommandRegistrationEvent
import dev.architectury.event.events.common.LifecycleEvent
import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import org.stardustmodding.interstellar.impl.command.DimensionTpCommand
import org.stardustmodding.interstellar.impl.config.InterstellarConfig
import org.stardustmodding.interstellar.impl.init.Initializer
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.PartitioningSerializer
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.RegistryKeys
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object Interstellar {
    const val MOD_ID = "interstellar"

    private val items = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM)

    // This isn't a real item!
    private val rocket = items.register(
        "rocket"
    ) { Item(Item.Settings()) }

    private val creativeTab =
        CreativeTabRegistry.create(Text.translatable(id("creative_tab").toTranslationKey())
        ) {
            ItemStack(
                rocket.get()
            )
        }

    private var config: InterstellarConfig? = null

    private var commands = listOf(
        DimensionTpCommand()
    )

    @JvmStatic
    fun id(id: String): Identifier {
        return Identifier(MOD_ID, id)
    }

    fun getConfig(): InterstellarConfig? {
        return config
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
            PartitioningSerializer.wrap<InterstellarConfig, ConfigData?> {
                definition: Config?, configClass: Class<ConfigData?>? ->
                    Toml4jConfigSerializer(
                        definition,
                        configClass
                    )
            }
        )

        config = AutoConfig.getConfigHolder(InterstellarConfig::class.java).config
        items.register()

        LifecycleEvent.SERVER_STARTED.register(Initializer::init)

        CommandRegistrationEvent.EVENT.register(CommandRegistrationEvent {
                dispatcher: CommandDispatcher<ServerCommandSource>,
                _: CommandRegistryAccess,
                _: CommandManager.RegistrationEnvironment ->
                for (cmd in commands) {
                    cmd.register(dispatcher)
                }
        })
    }
}