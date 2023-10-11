package dev.niostone.interstellar.fabric;

import dev.niostone.interstellar.Interstellar;
import net.fabricmc.api.ModInitializer;

public class InterstellarFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Interstellar.init();
    }
}
