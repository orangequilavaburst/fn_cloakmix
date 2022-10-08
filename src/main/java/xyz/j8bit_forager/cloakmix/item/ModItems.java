package xyz.j8bit_forager.cloakmix.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CloakMix.MOD_ID);

    // basic items

    public static final RegistryObject<Item> REAPER_CLOTH = ITEMS.register("reaper_cloth",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.CLOAKMIX_TAB)));

    public static void register(IEventBus eventBus){

        ITEMS.register(eventBus);

    }

}