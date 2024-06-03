package org.stardustmodding.interstellar.api.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext

interface ICommand<S> {
    fun register(dispatcher: CommandDispatcher<S>)
    fun execute(ctx: CommandContext<S>): Int

    fun literal(name: String): LiteralArgumentBuilder<S> {
        return LiteralArgumentBuilder.literal(name)
    }

    fun <T> argument(name: String, type: ArgumentType<T>): RequiredArgumentBuilder<S, T> {
        return RequiredArgumentBuilder.argument(name, type)
    }
}
