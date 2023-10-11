package dev.niostone.interstellar.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.niostone.interstellar.Interstellar;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Interstellar.MOD_ID)
public class InterstellarForge {
    public InterstellarForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Interstellar.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Interstellar.init();
    }
}
