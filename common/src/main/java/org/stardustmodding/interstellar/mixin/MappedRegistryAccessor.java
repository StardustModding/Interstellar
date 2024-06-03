package org.stardustmodding.interstellar.mixin;

import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(SimpleRegistry.class)
public interface MappedRegistryAccessor<T> {
    @Accessor("nextId")
    void setNextId(int nextId);

    @Accessor("tagToEntryList")
    Map<TagKey<T>, RegistryEntryList.Named<T>> tags();

    @Accessor("valueToEntry")
    @Nullable Map<T, RegistryEntry.Reference<T>> getUnregisteredIntrusiveHolders();

    @Accessor("rawIdToEntry")
    ObjectList<RegistryEntry.Reference<T>> getById();

    @Accessor("entryToRawId")
    Object2IntMap<T> getToId();

    @Accessor("idToEntry")
    Map<Identifier, RegistryEntry.Reference<T>> getByLocation();

    @Accessor("keyToEntry")
    Map<RegistryKey<T>, RegistryEntry.Reference<T>> getByKey();

    @Accessor("valueToEntry")
    Map<T, RegistryEntry.Reference<T>> getByValue();

    @Accessor("entryToLifecycle")
    Map<T, Lifecycle> getLifecycles();

    @Accessor("frozen")
    boolean isFrozen();

    @Accessor("frozen")
    void setFrozen(boolean frozen);

    @Accessor("cachedEntries")
    void setHoldersInOrder(List<RegistryEntry.Reference<T>> o);

    @Accessor("lifecycle")
    void setRegistryLifecycle(Lifecycle base);
}
