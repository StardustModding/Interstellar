package org.stardustmodding.interstellar.api.data

import net.minecraft.server.world.ServerWorld

object StateUtil {
    inline fun <reified T> load(world: ServerWorld): T where T : SavedState {
        return world.persistentStateManager.getOrCreate(
            { SavedState.readStatic<T>(it) },
            { SavedState.defaultStatic<T>() },
            SavedState.idStatic<T>().toString()
        )
    }

    fun <T> write(world: ServerWorld, obj: T) where T : SavedState {
        if (!obj.shouldWrite) return

        world.persistentStateManager.set(obj.id.toString(), obj)
        world.persistentStateManager.save()
    }
}
