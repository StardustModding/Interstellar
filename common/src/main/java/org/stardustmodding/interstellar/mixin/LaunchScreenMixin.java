package org.stardustmodding.interstellar.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.util.math.ColorHelper.Argb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.stardustmodding.interstellar.impl.Interstellar;

@Environment(EnvType.CLIENT)
@Mixin(SplashOverlay.class)
public class LaunchScreenMixin {
    /**
     * @author RedstoneWizard08
     * @reason Register the dark logo texture
     */
    @Overwrite
    public static void init(MinecraftClient minecraft) {
        minecraft.getTextureManager().registerTexture(Interstellar.id("textures/gui/loading_logo.png"), new SplashOverlay.LogoTexture());
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void render(DrawContext guiGraphics, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        SplashOverlay.MOJANG_RED = Argb.getArgb(255, 31, 33, 32);
    }
}
