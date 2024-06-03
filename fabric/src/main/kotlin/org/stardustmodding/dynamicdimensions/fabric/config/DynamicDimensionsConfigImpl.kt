package org.stardustmodding.dynamicdimensions.fabric.config

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import net.fabricmc.loader.api.FabricLoader
import org.jetbrains.annotations.ApiStatus
import org.jetbrains.annotations.Contract
import org.stardustmodding.dynamicdimensions.impl.Constants
import org.stardustmodding.dynamicdimensions.impl.config.DynamicDimensionsConfig
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files

@ApiStatus.Internal
class DynamicDimensionsConfigImpl private constructor(
    @field:Expose private var allowDimensionCreation: Boolean = true,
    @field:Expose private var deleteRemovedDimensions: Boolean = false,
    @field:Expose private var deleteDimensionsWithPlayers: Boolean = true,
    @field:Expose private var enableCommands: Boolean = false,
    @field:Expose private var commandPermissionLevel: Int = 2
) : DynamicDimensionsConfig {
    override fun allowDimensionCreation(): Boolean {
        return this.allowDimensionCreation
    }

    override fun deleteRemovedDimensions(): Boolean {
        return this.deleteRemovedDimensions
    }

    override fun deleteDimensionsWithPlayers(): Boolean {
        return this.deleteDimensionsWithPlayers
    }

    override fun enableCommands(): Boolean {
        return this.enableCommands
    }

    override fun commandPermissionLevel(): Int {
        return this.commandPermissionLevel
    }

    override fun allowDimensionCreation(value: Boolean) {
        this.allowDimensionCreation = value
    }

    override fun deleteRemovedDimensions(value: Boolean) {
        this.deleteRemovedDimensions = value
    }

    override fun deleteDimensionsWithPlayers(value: Boolean) {
        this.deleteDimensionsWithPlayers = value
    }

    override fun enableCommands(value: Boolean) {
        this.enableCommands = value
    }

    override fun commandPermissionLevel(value: Int) {
        this.commandPermissionLevel = value
    }

    @Contract(pure = true)
    override fun toString(): String {
        return "DynamicDimensionsConfigImpl{" +
                "allowDimensionCreation=" + allowDimensionCreation +
                ", deleteRemovedDimensions=" + deleteRemovedDimensions +
                ", deleteDimensionsWithPlayers=" + deleteDimensionsWithPlayers +
                ", enableCommands=" + enableCommands +
                ", commandPermissionLevel=" + commandPermissionLevel +
                '}'
    }

    companion object {
        private val GSON: Gson = GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        @JvmStatic
        fun create(): DynamicDimensionsConfigImpl {
            val file = FabricLoader.getInstance().configDir.resolve("dynamic_dimensions.json").toFile()
            if (file.exists()) {
                try {
                    FileReader(file, StandardCharsets.UTF_8).use { json ->
                        val config = GSON.fromJson(json, DynamicDimensionsConfigImpl::class.java)
                        if (config != null) {
                            return config
                        } else {
                            throw RuntimeException("Dynamic Dimensions: Failed to read configuration file!")
                        }
                    }
                } catch (e: IOException) {
                    throw RuntimeException("Dynamic Dimensions: Failed to read configuration file!", e)
                }
            } else {
                file.parentFile.mkdirs()
                val config = DynamicDimensionsConfigImpl()
                try {
                    Files.createDirectories(FabricLoader.getInstance().configDir)
                    FileWriter(file, StandardCharsets.UTF_8).use { writer ->
                        GSON.toJson(config, writer)
                    }
                } catch (e: IOException) {
                    Constants.LOGGER.error("Failed to write config file!", e)
                }
                return config
            }
        }
    }
}
