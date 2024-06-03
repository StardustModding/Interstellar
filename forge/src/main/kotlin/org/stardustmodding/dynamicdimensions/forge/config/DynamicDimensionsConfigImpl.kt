package org.stardustmodding.dynamicdimensions.forge.config

import net.minecraftforge.common.ForgeConfigSpec
import org.stardustmodding.dynamicdimensions.impl.config.DynamicDimensionsConfig

class DynamicDimensionsConfigImpl private constructor(builder: ForgeConfigSpec.Builder) : DynamicDimensionsConfig {
    private val allowDimensionCreation: ForgeConfigSpec.BooleanValue = builder
        .comment("Set this to false to disable DynamicDimensions")
        .translation("dynamicdimensions.config.allow_dimension_creation")
        .define("allow_dimension_creation", true)
    private val deleteRemovedDimensions: ForgeConfigSpec.BooleanValue = builder
        .comment("Set this to true to permanently delete the files of removed dimensions")
        .translation("dynamicdimensions.config.delete_removed_dimensions")
        .define("delete_removed_dimensions", false)
    private val deleteDimensionsWithPlayers: ForgeConfigSpec.BooleanValue = builder
        .comment("Set this to true to permanently delete the files of removed dimensions")
        .translation("dynamicdimensions.config.delete_dimensions_with_players")
        .define("delete_dimensions_with_players", true)
    private val enableCommands: ForgeConfigSpec.BooleanValue = builder
        .comment("Set this to true to enable commands")
        .translation("dynamicdimensions.config.enable_commands")
        .define("enable_commands", false)
    private val commandPermissionLevel: ForgeConfigSpec.IntValue = builder
        .comment("Set this to true to enable commands")
        .translation("dynamicdimensions.config.command_permission_level")
        .defineInRange("command_permission_level", 2, 0, 99)

    override fun allowDimensionCreation(): Boolean {
        return allowDimensionCreation.get()
    }

    override fun deleteRemovedDimensions(): Boolean {
        return deleteRemovedDimensions.get()
    }

    override fun deleteDimensionsWithPlayers(): Boolean {
        return deleteDimensionsWithPlayers.get()
    }

    override fun enableCommands(): Boolean {
        return enableCommands.get()
    }

    override fun commandPermissionLevel(): Int {
        return commandPermissionLevel.get()
    }

    override fun allowDimensionCreation(value: Boolean) {
        allowDimensionCreation.set(value)
    }

    override fun deleteRemovedDimensions(value: Boolean) {
        deleteRemovedDimensions.set(value)
    }

    override fun deleteDimensionsWithPlayers(value: Boolean) {
        deleteDimensionsWithPlayers.set(value)
    }

    override fun enableCommands(value: Boolean) {
        enableCommands.set(value)
    }

    override fun commandPermissionLevel(value: Int) {
        commandPermissionLevel.set(value)
    }

    companion object {
        val INSTANCE: DynamicDimensionsConfigImpl
        val SPEC: ForgeConfigSpec

        init {
            val pair = ForgeConfigSpec.Builder()
                .configure { builder: ForgeConfigSpec.Builder -> DynamicDimensionsConfigImpl(builder) }
            INSTANCE = pair.left
            SPEC = pair.right
        }
    }
}
