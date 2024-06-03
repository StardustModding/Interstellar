package org.stardustmodding.interstellar.mixin;

import org.stardustmodding.dynamicdimensions.impl.accessor.PrimaryLevelDataAccessor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.level.LevelProperties;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LevelProperties.class)
public abstract class PrimaryLevelDataMixin implements PrimaryLevelDataAccessor {
    private @Unique List<RegistryKey<World>> dynamicDimensions = null;

    @Inject(method = "updateProperties", at = @At("RETURN"))
    private void skipWritingDynamicDimensions(DynamicRegistryManager registryManager, @NotNull NbtCompound levelNbt, NbtCompound playerNbt, CallbackInfo ci) {
        if (this.dynamicDimensions != null) {
            NbtCompound dimensions = levelNbt.getCompound("WorldGenSettings").getCompound("dimensions");
            for (RegistryKey<World> dynamicDimension : this.dynamicDimensions) {
                dimensions.remove(dynamicDimension.getValue().toString());
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setDynamicList(@NotNull List<? extends RegistryKey<World>> dynamicDimensions) {
        this.dynamicDimensions = (List<RegistryKey<World>>) dynamicDimensions;
    }
}
