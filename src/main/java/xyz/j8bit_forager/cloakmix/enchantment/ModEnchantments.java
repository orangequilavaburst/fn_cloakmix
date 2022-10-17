package xyz.j8bit_forager.cloakmix.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CloakMix.MOD_ID);

    public static RegistryObject<Enchantment> ALTERED_SIGHT = ENCHANTMENTS.register("altered_sight",
            () -> new EnchantmentAlteredSight(Enchantment.Rarity.RARE, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));

    public static RegistryObject<Enchantment> ANONYMITY = ENCHANTMENTS.register("anonymity",
            () -> new EnchantmentAlteredSight(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR_HEAD, EquipmentSlot.HEAD));

    public static void register(IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }

}
