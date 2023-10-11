package dev.niostone.interstellar;

import com.google.common.base.Suppliers;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class Interstellar {
    public static final String MOD_ID = "interstellar";
    public static final Supplier<Registries> REGISTRIES = Suppliers.memoize(() -> Registries.get(MOD_ID));
    
    public static final CreativeModeTab TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "creative_tab"), () ->
            new ItemStack(Interstellar.ROCKET.get()));
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);

    // This shouldn't be in a tab, it's not an actual item.
    public static final RegistrySupplier<Item> ROCKET = ITEMS.register("rocket", () ->
            new Item(new Item.Properties()));
    
    public static void init() {
        ITEMS.register();
        
        System.out.println(ModExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
