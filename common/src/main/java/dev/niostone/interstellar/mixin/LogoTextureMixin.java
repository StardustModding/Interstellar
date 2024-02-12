package dev.niostone.interstellar.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import dev.niostone.interstellar.impl.Interstellar;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.IOException;
import java.io.InputStream;

@Environment(EnvType.CLIENT)
@Mixin(LoadingOverlay.LogoTexture.class)
public abstract class LogoTextureMixin extends SimpleTexture {
    public LogoTextureMixin(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    /**
     * @author RedstoneWizard08
     * @reason Replacing the logo texture
     */
    @Overwrite
    @SuppressWarnings("DataFlowIssue")
    public @NotNull SimpleTexture.TextureImage getTextureImage(ResourceManager resourceManager) {
        this.location = new ResourceLocation(Interstellar.MOD_ID, "textures/gui/loading_logo.png");

        try {
            InputStream is = Interstellar.class.getClassLoader().getResourceAsStream("assets/interstellar/textures/gui/loading_logo.png");
            SimpleTexture.TextureImage tex;

            try {
                NativeImage img = NativeImage.read(is);
                tex = new SimpleTexture.TextureImage(new TextureMetadataSection(true, true), img);
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
            return new SimpleTexture.TextureImage(ex);
        }
    }
}
