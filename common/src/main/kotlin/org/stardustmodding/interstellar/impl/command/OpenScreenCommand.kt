package org.stardustmodding.interstellar.impl.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.world.inventory.MenuType
import org.stardustmodding.interstellar.api.client.gui.SharedScreen
import org.stardustmodding.interstellar.api.command.ICommand
import org.stardustmodding.interstellar.api.command.MapArgumentType
import org.stardustmodding.interstellar.impl.init.Screens

class OpenScreenCommand : ICommand<CommandSourceStack> {
    private val arg = MapArgumentType.create(mapOf("suit_customization" to Screens.SUIT_CUSTOMIZATION_HANDLER))

    override fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            literal("openscreen")
                .then(
                    argument("screen", arg)
                        .executes(this::execute)
                )
        )
    }

    override fun execute(ctx: CommandContext<CommandSourceStack>): Int {
        val handler = arg.get<MenuType<*>>(ctx, "screen")

        ctx.source.player?.let { SharedScreen.open(it, handler) }

        return 0
    }
}