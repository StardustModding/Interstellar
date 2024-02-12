package dev.niostone.interstellar.impl

import com.mojang.brigadier.CommandDispatcher
import dev.architectury.event.events.common.CommandRegistrationEvent
import dev.architectury.event.events.common.LifecycleEvent
import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.DeferredRegister
import dev.niostone.interstellar.impl.command.DimensionTpCommand
import dev.niostone.interstellar.impl.config.InterstellarConfig
import dev.niostone.interstellar.impl.util.RegistryLookup
import dev.niostone.interstellar.impl.init.Biomes
import dev.niostone.interstellar.impl.init.Planets
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.PartitioningSerializer
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

object Interstellar {
    const val MOD_ID = "interstellar"

    private val items = DeferredRegister.create(MOD_ID, Registries.ITEM)

    // This isn't a real item!
    private val rocket = items.register(
        "rocket"
    ) { Item(Item.Properties()) }

    private val creativeTab =
        CreativeTabRegistry.create(Component.translatable(ResourceLocation(MOD_ID, "creative_tab").toLanguageKey())
        ) {
            ItemStack(
                rocket.get()
            )
        }

    private var config: InterstellarConfig? = null

    private var commands = listOf(
        DimensionTpCommand()
    )

    fun getConfig(): InterstellarConfig? {
        return config
    }

    fun getItems(): DeferredRegister<Item> {
        return items
    }

    fun getTab(): CreativeModeTab {
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

        LifecycleEvent.SERVER_STARTED.register(LifecycleEvent.ServerState { server: MinecraftServer ->
            RegistryLookup.init(server)
            Biomes.register()
            Planets.register(server)
        })

        CommandRegistrationEvent.EVENT.register(CommandRegistrationEvent {
            dispatcher: CommandDispatcher<CommandSourceStack>,
            _: CommandBuildContext,
            _: Commands.CommandSelection ->
                for (cmd in commands) {
                    cmd.register(dispatcher)
                }
        })
    }
}