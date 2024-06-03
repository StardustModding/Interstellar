package org.stardustmodding.interstellar.mixin;

import net.minecraft.registry.tag.TagManagerLoader;
import net.minecraft.server.DataPackContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DataPackContents.class)
public interface ReloadableServerResourcesAccessor {
    @Accessor("registryTagManager")
    TagManagerLoader getTagManager();
}
