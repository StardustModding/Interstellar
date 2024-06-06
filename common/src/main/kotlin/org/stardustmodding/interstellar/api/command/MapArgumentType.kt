package org.stardustmodding.interstellar.api.command

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.minecraft.command.CommandSource
import net.minecraft.command.argument.ArgumentTypes
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import org.stardustmodding.interstellar.impl.Interstellar
import org.stardustmodding.interstellar.impl.Interstellar.id
import java.util.concurrent.CompletableFuture

class MapArgumentType<T>(private val options: Map<String, T>): ArgumentType<T> {
    private val err = DynamicCommandExceptionType { Text.literal("Invalid option: '$it'") }

    override fun <S : Any?> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        return CommandSource.suggestMatching(options.keys, builder)
    }

    override fun parse(reader: StringReader): T? {
        val it = reader.readString()

        if (!options.contains(it)) {
            throw err.create(it)
        }

        return options[it]
    }

    inline fun <reified T>get(context: CommandContext<*>, name: String?): T {
        return context.getArgument(name, T::class.java)
    }

    companion object {
        fun <T>create(options: Map<String, T>): MapArgumentType<T> {
            val ty = MapArgumentType(options)
            ArgumentTypes.register(Registries.COMMAND_ARGUMENT_TYPE, id("map_argument_${options.hashCode()}").toString(), MapArgumentType::class.java, ConstantArgumentSerializer.of { _ -> ty })
            return ty
        }
    }
}