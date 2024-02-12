package dev.niostone.interstellar.mixin;

import dev.niostone.interstellar.impl.Interstellar;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LoadingOverlay.class)
public class LaunchScreenMixin {
    /**
     * @author RedstoneWizard08
     * @reason Registering custom image
     */
    @Overwrite
    public static void registerTextures(Minecraft minecraft) {
        minecraft.getTextureManager().register(new ResourceLocation(Interstellar.MOD_ID, "textures/gui/loading_logo.png"), new LoadingOverlay.LogoTexture());
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        LoadingOverlay.LOGO_BACKGROUND_COLOR = FastColor.ARGB32.color(255, 31, 33, 32);
    }
}
