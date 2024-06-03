package org.stardustmodding.dynamicdimensions.impl.accessor

import net.minecraft.registry.RegistryKey
import net.minecraft.world.World
import org.jetbrains.annotations.ApiStatus

/**
 * Saves and loads dynamic dimension registrations from the world file
 */
@ApiStatus.Internal
interface PrimaryLevelDataAccessor {
    fun setDynamicList(dynamicDimensions: List<RegistryKey<World?>?>)
}
