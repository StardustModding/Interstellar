package org.stardustmodding.interstellar.api.data

import net.minecraft.server.level.ServerLevel

object StateUtil {
    inline fun <reified T> load(world: ServerLevel): T where T : SavedState {
        return world.dataStorage.computeIfAbsent(
            { SavedState.readStatic<T>(it) },
            { SavedState.defaultStatic<T>() },
            SavedState.idStatic<T>().toString()
        )
    }

    fun <T> write(world: ServerLevel, obj: T) where T : SavedState {
        if (!obj.shouldWrite) return

        world.dataStorage.set(obj.id.toString(), obj)
        world.dataStorage.save()
    }
}
