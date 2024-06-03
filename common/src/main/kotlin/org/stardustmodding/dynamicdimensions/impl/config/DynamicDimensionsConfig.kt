package org.stardustmodding.dynamicdimensions.impl.config

interface DynamicDimensionsConfig {
    fun applyDefaultValues() {
        allowDimensionCreation(true)
        deleteRemovedDimensions(false)
        deleteDimensionsWithPlayers(false)
        enableCommands(false)
        commandPermissionLevel(2)
    }

    fun allowDimensionCreation(): Boolean
    fun deleteRemovedDimensions(): Boolean
    fun deleteDimensionsWithPlayers(): Boolean
    fun enableCommands(): Boolean
    fun commandPermissionLevel(): Int
    fun allowDimensionCreation(value: Boolean)
    fun deleteRemovedDimensions(value: Boolean)
    fun deleteDimensionsWithPlayers(value: Boolean)
    fun enableCommands(value: Boolean)
    fun commandPermissionLevel(value: Int)
}
