package net.minecraft.resources;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ResourceLocation extends Identifier {
    public ResourceLocation(String id) {
        super(id);
    }

    public ResourceLocation(String namespace, String path) {
        super(namespace, path);
    }

    protected ResourceLocation(String namespace, String path, @Nullable Identifier.ExtraData extraData) {
        super(namespace, path, extraData);
    }
}
