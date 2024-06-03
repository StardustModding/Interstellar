package org.stardustmodding.interstellar.mixin;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(RegistryEntryList.Named.class)
public interface RegistryEntryListNamedAccessor<T> {
    @Accessor("entries")
    List<RegistryEntry<T>> getContents();

    @Accessor("entries")
    void setContents(List<RegistryEntry<T>> contents);
}
