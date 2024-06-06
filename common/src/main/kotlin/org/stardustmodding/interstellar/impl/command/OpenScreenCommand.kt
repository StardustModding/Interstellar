package org.stardustmodding.interstellar.impl.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.server.command.ServerCommandSource
import org.stardustmodding.interstellar.api.client.gui.SharedScreen
import org.stardustmodding.interstellar.api.command.ICommand
import org.stardustmodding.interstellar.api.command.MapArgumentType
import org.stardustmodding.interstellar.impl.init.Screens

class OpenScreenCommand: ICommand<ServerCommandSource> {
    private val arg = MapArgumentType.create(mapOf("suit_customization" to Screens.SUIT_CUSTOMIZATION_HANDLER))

    override fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            literal("openscreen")
                .then(
                    argument("screen", arg)
                        .executes(this::execute)
                )
        )
    }

    override fun execute(ctx: CommandContext<ServerCommandSource>): Int {
        val handler = arg.get<ScreenHandlerType<*>>(ctx, "screen")

        ctx.source.player?.let { SharedScreen.open(it, handler) }

        return 0
    }
}