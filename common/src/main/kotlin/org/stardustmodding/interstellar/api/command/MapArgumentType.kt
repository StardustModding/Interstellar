package org.stardustmodding.interstellar.api.command

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.commands.CommandSource
import net.minecraft.commands.SharedSuggestionProvider
import net.minecraft.commands.synchronization.ArgumentTypeInfos
import net.minecraft.commands.synchronization.SingletonArgumentInfo
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import org.stardustmodding.interstellar.impl.Interstellar.id
import java.util.concurrent.CompletableFuture

class MapArgumentType<T>(private val options: Map<String, T>) : ArgumentType<T> {
    private val err = DynamicCommandExceptionType { Component.literal("Invalid option: '$it'") }

    override fun <S : Any?> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        return SharedSuggestionProvider.suggest(options.keys, builder)
    }

    override fun parse(reader: StringReader): T? {
        val it = reader.readString()

        if (!options.contains(it)) {
            throw err.create(it)
        }

        return options[it]
    }

    inline fun <reified T> get(context: CommandContext<*>, name: String?): T {
        return context.getArgument(name, T::class.java)
    }

    companion object {
        fun <T> create(options: Map<String, T>): MapArgumentType<T> {
            val ty = MapArgumentType(options)
            ArgumentTypeInfos.register(
                BuiltInRegistries.COMMAND_ARGUMENT_TYPE,
                id("map_argument_${options.hashCode()}").toString(),
                MapArgumentType::class.java,
                SingletonArgumentInfo.contextAware { _ -> ty })
            return ty
        }
    }
}