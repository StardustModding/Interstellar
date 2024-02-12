package dev.niostone.interstellar.impl.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import dev.niostone.interstellar.api.command.ICommand
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.arguments.DimensionArgument
import net.minecraft.server.level.ServerLevel

class DimensionTpCommand: ICommand<CommandSourceStack> {
    override fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        dispatcher.register(
                literal("dtp")
                        .then(argument("dimension", DimensionArgument.dimension())
                                .executes(this::execute)
                        )
        );
    }

    override fun execute(ctx: CommandContext<CommandSourceStack>): Int {
        var dim: ServerLevel?

        try {
            dim = DimensionArgument.getDimension(ctx, "dimension")
        } catch (e: CommandSyntaxException) {
            return 0
        }

        if (!ctx.source.isPlayer) {
            return 0
        }

        if (dim != null) {
            ctx.source.player?.changeDimension(dim)
        }

        return 1
    }
}
