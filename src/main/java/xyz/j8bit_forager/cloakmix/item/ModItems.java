package xyz.j8bit_forager.cloakmix.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.item.custom.ModDyeableCloakItem;
import xyz.j8bit_forager.cloakmix.item.custom.ModSanguineDagger;
import xyz.j8bit_forager.cloakmix.item.custom.ModVorpalBlade;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CloakMix.MOD_ID);

    // basic items

    public static final RegistryObject<Item> REAPER_CLOTH = ITEMS.register("reaper_cloth",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.CLOAKMIX_ITEMS_TAB)));

    public static final RegistryObject<Item> BASIC_CLOAK = ITEMS.register("basic_cloak",
            () -> new ModDyeableCloakItem(ModArmorTiers.CLOTH, new Item.Properties().tab(ModCreativeModeTab.CLOAKMIX_ITEMS_TAB)));

    public static final RegistryObject<Item> SANGUINE_DAGGER = ITEMS.register("sanguine_dagger",
            () -> new ModSanguineDagger(new Item.Properties().tab(ModCreativeModeTab.CLOAKMIX_ITEMS_TAB).durability(666).rarity(Rarity.RARE), 1.5f, 2.5f, 10, Items.GHAST_TEAR));

    public static final RegistryObject<Item> VORPAL_BLADE = ITEMS.register("vorpal_blade",
            () -> new ModVorpalBlade(new Item.Properties().tab(ModCreativeModeTab.CLOAKMIX_ITEMS_TAB).durability(666).rarity(Rarity.RARE), 4.5f, -2.0f, ModItems.REAPER_CLOTH.get()));
    public static void register(IEventBus eventBus){

        ITEMS.register(eventBus);

    }

}
