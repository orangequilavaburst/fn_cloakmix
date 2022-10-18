package xyz.j8bit_forager.cloakmix.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.item.custom.ModCloakItem;
import xyz.j8bit_forager.cloakmix.item.custom.ModDyeableCloakItem;

public class ModEnchantments {

    // enchantments

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CloakMix.MOD_ID);

    public static RegistryObject<Enchantment> ALTERED_SIGHT = ENCHANTMENTS.register("altered_sight",
            () -> new EnchantmentAlteredSight(Enchantment.Rarity.RARE, ModEnchantments.CLOAK, EquipmentSlot.HEAD));

    public static RegistryObject<Enchantment> ANONYMITY = ENCHANTMENTS.register("anonymity",
            () -> new EnchantmentAlteredSight(Enchantment.Rarity.UNCOMMON, ModEnchantments.CLOAK, EquipmentSlot.HEAD));

    public static RegistryObject<Enchantment> BILLOWING = ENCHANTMENTS.register("billowing",
            () -> new EnchantmentAlteredSight(Enchantment.Rarity.UNCOMMON, ModEnchantments.CLOAK, EquipmentSlot.HEAD));

    public static void register(IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }

    // enchantment categories

    public static EnchantmentCategory CLOAK = EnchantmentCategory.create("cloak",
            ModEnchantments::canApplyEnchantment);

    public static boolean canApplyEnchantment(Item item) {
        //Do some calculations and return whether can or can't use the enchant on the item
        return item instanceof ModCloakItem || item instanceof ModDyeableCloakItem;
    }

}
