package org.stardustmodding.interstellar.impl.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.command.argument.DimensionArgumentType
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.world.ServerWorld
import org.stardustmodding.interstellar.api.command.ICommand

class DimensionTpCommand : ICommand<ServerCommandSource> {
    override fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            literal("dtp")
                .then(
                    argument("dimension", DimensionArgumentType.dimension())
                        .executes(this::execute)
                )
        );
    }

    override fun execute(ctx: CommandContext<ServerCommandSource>): Int {
        val dim: ServerWorld?

        try {
            dim = DimensionArgumentType.getDimensionArgument(ctx, "dimension")
        } catch (e: CommandSyntaxException) {
            return 0
        }

        if (!ctx.source.isExecutedByPlayer) {
            return 0
        }

        if (dim != null) {
            val x = ctx.source.player?.x!!
            val y = ctx.source.player?.y!!
            val z = ctx.source.player?.z!!

            ctx.source.player?.teleport(dim, x, y, z, 0.0f, 0.0f)
        }

        return 1
    }
}
