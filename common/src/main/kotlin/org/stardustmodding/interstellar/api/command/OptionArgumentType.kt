package org.stardustmodding.interstellar.api.command

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.commands.synchronization.ArgumentTypeInfos
import net.minecraft.commands.synchronization.SingletonArgumentInfo
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import org.stardustmodding.interstellar.impl.Interstellar.id
import java.util.concurrent.CompletableFuture

class OptionArgumentType(private val options: List<String>) : ArgumentType<String> {
    private val err = DynamicCommandExceptionType { Component.literal("Invalid option: '$it'") }

    override fun <S : Any?> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        return SharedSuggestionProvider.suggest(options, builder)
    }

    override fun parse(reader: StringReader): String {
        val it = reader.readString()

        if (!options.contains(it)) {
            throw err.create(it)
        }

        return it
    }

    fun get(context: CommandContext<*>, name: String?): String {
        return context.getArgument(name, String::class.java)
    }

    companion object {
        fun create(vararg options: String): OptionArgumentType {
            val ty = OptionArgumentType(options.toList())
            ArgumentTypeInfos.register(
                BuiltInRegistries.COMMAND_ARGUMENT_TYPE,
                id("option_argument_${options.hashCode()}").toString(),
                OptionArgumentType::class.java,
                SingletonArgumentInfo.contextAware { _ -> ty })
            return ty
        }
    }
}