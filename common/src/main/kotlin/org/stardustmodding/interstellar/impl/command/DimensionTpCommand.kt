package org.stardustmodding.interstellar.impl.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.DimensionArgument
import net.minecraft.server.level.ServerLevel
import org.stardustmodding.interstellar.api.command.ICommand

class DimensionTpCommand : ICommand<CommandSourceStack> {
    override fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
            literal("dtp")
                .then(
                    argument("dimension", DimensionArgument.dimension())
                        .executes(this::execute)
                )
        )
    }

    override fun execute(ctx: CommandContext<CommandSourceStack>): Int {
        val dim: ServerLevel?

        try {
            dim = DimensionArgument.getDimension(ctx, "dimension")
        } catch (e: CommandSyntaxException) {
            return 0
        }

        if (!ctx.source.isPlayer) {
            return 0
        }

        if (dim != null) {
            val x = ctx.source.player?.x!!
            val y = ctx.source.player?.y!!
            val z = ctx.source.player?.z!!

            ctx.source.player?.teleportTo(dim, x, y, z, 0.0f, 0.0f)
        }

        return 1
    }
}
