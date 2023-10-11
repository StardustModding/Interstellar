package dev.niostone.interstellar.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.resources.ResourceLocation;

@Config(name = "gas")
public class GasConfig implements ConfigData {
    public String id;
    public float quantity;

    public GasConfig(String id, float quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public GasConfig(ResourceLocation id, float quantity) {
        this.id = id.toString();
        this.quantity = quantity;
    }

    public GasConfig(String namespace, String id, float quantity) {
        this.id = namespace + ":" + id;
        this.quantity = quantity;
    }
}
