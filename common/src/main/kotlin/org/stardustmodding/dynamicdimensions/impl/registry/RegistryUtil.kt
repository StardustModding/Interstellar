package org.stardustmodding.dynamicdimensions.impl.registry

import com.google.common.collect.ImmutableList
import com.mojang.serialization.Lifecycle
import net.minecraft.registry.*
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.util.Identifier
import org.stardustmodding.dynamicdimensions.impl.Constants
import org.stardustmodding.interstellar.mixin.MappedRegistryAccessor
import org.stardustmodding.interstellar.mixin.RegistryEntryListNamedAccessor

object RegistryUtil {
    @JvmStatic
    fun <T> unregister(registry: Registry<T>, id: Identifier) {
        if (registry.containsId(id)) {
            if (registry is SimpleRegistry<T>) {
                if (registry is DefaultedRegistry<*>) {
                    require(registry.defaultId != id) { "Cannot remove default value in registry!" }
                }

                val accessor = (registry as MappedRegistryAccessor<*>)

                val type = accessor.byLocation[id]!!.value()
                val byId = accessor.byId
                if (byId.size <= accessor.toId.getInt(type)) {
                    Constants.LOGGER.warn("ID mismatch in registry '{}'", registry.getKey())
                }
                accessor.toId.removeInt(type)

                var success = false
                for (i in byId.indices) {
                    val reference = byId[i]
                    if (reference != null) {
                        if (reference.registryKey().value == id) {
                            byId[i] = null
                            success = true
                            var max = 0
                            for (i1 in byId.indices) {
                                max = if (byId[i1] != null) i1 else max
                            }
                            byId.size(max + 1)
                            accessor.setNextId(max)
                            break
                        }
                    }
                }

                assert(success)
                accessor.byLocation.remove(id)
                accessor.byKey.remove(RegistryKey.of(registry.getKey(), id))
                accessor.byValue.remove(type)
                accessor.lifecycles.remove(type)
                val base = Lifecycle.stable()
                for (value in accessor.lifecycles.values) {
                    base.add(value)
                }
                accessor.setRegistryLifecycle(base)
                for (holderSet in accessor.tags().values) {
                    val set = holderSet as RegistryEntryListNamedAccessor<T>
                    val list = ImmutableList.builder<RegistryEntry<T>>()
                    for (content in set.contents) {
                        if (!content.matchesId(id)) list.add(content)
                    }
                    set.setContents(list.build())
                }
                if (accessor.unregisteredIntrusiveHolders != null) {
                    accessor.unregisteredIntrusiveHolders!!.remove(type)
                }
                accessor.setHoldersInOrder(null)
            }
        } else {
            Constants.LOGGER.warn("Tried to remove non-existent key {}", id)
        }
    }

    @JvmStatic
    fun <T> registerUnfreeze(registry: Registry<T>, id: Identifier, value: T): RegistryEntry.Reference<T> {
        if (!registry.containsId(id)) {
            if (registry.javaClass == SimpleRegistry::class.java || registry.javaClass == SimpleDefaultedRegistry::class.java) {
                val mapped = registry as SimpleRegistry<T>
                val accessor = registry as MappedRegistryAccessor<*>
                val frozen = accessor.isFrozen
                if (frozen) accessor.isFrozen = false
                val ref = mapped.add(RegistryKey.of(registry.getKey(), id), value, Lifecycle.stable())
                if (frozen) registry.freeze()
                checkNotNull(accessor.byId[accessor.toId.getInt(value)])
                return ref
            } else {
                throw IllegalStateException("Dynamic Dimensions: Non-vanilla '" + registry.key.value + "' registry! " + registry.javaClass.name)
            }
        } else {
            Constants.LOGGER.warn("Tried to add pre-existing key$id")
            return registry.entryOf(RegistryKey.of(registry.key, id))
        }
    }

    fun <T> registerUnfreezeExact(
        registry: Registry<T?>,
        rawId: Int,
        id: Identifier,
        value: T
    ): RegistryEntry.Reference<T?> {
        if (!registry.containsId(id)) {
            if (registry.javaClass == SimpleRegistry::class.java || registry.javaClass == SimpleDefaultedRegistry::class.java) {
                val mapped = registry as SimpleRegistry<T?>
                val accessor = registry as MappedRegistryAccessor<*>
                val frozen = accessor.isFrozen
                if (frozen) accessor.isFrozen = false
                val ref = mapped.set(rawId, RegistryKey.of(registry.getKey(), id), value, Lifecycle.stable())
                if (frozen) registry.freeze()
                return ref
            } else {
                throw IllegalStateException("Dynamic Dimensions: Non-vanilla '" + registry.key.value + "' registry! " + registry.javaClass.name)
            }
        } else {
            Constants.LOGGER.warn(
                "Tried to add pre-existing key $id with raw id $rawId (contains: " + registry.getId(
                    registry[id]
                ) + ")"
            )
            return registry.entryOf(RegistryKey.of(registry.key, id))
        }
    }
}
