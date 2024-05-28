package org.stardustmodding.interstellar.mixin;

import org.stardustmodding.interstellar.impl.Interstellar;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.IOException;
import java.io.InputStream;

@Environment(EnvType.CLIENT)
@Mixin(SplashOverlay.LogoTexture.class)
public abstract class LogoTextureMixin extends ResourceTexture {
    public LogoTextureMixin(Identifier resourceLocation) {
        super(resourceLocation);
    }

    /**
     * @author RedstoneWizard08
     * @reason Replacing the logo texture
     */
    @Overwrite
    @SuppressWarnings("DataFlowIssue")
    public @NotNull ResourceTexture.TextureData loadTextureData(ResourceManager resourceManager) {
        this.location = Interstellar.id("textures/gui/loading_logo.png");

        try {
            InputStream is = Interstellar.class.getClassLoader().getResourceAsStream("assets/interstellar/textures/gui/loading_logo.png");
            ResourceTexture.TextureData tex;

            try {
                NativeImage img = NativeImage.read(is);
                tex = new ResourceTexture.TextureData(new TextureResourceMetadata(true, true), img);
            } catch (Throwable ex) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Throwable err) {
                        ex.addSuppressed(err);
                    }
                }

                throw ex;
            }

            is.close();

            return tex;
        } catch (IOException ex) {
            return new ResourceTexture.TextureData(ex);
        }
    }
}
