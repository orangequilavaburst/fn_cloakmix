package xyz.j8bit_forager.cloakmix.enchantment;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

public class EnchantmentMagicProtection extends Enchantment {

    protected EnchantmentMagicProtection(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot... pApplicableSlots) {
        super(pRarity, ModEnchantments.CLOAK, pApplicableSlots);
    }

    public int getMinCost(int pEnchantmentLevel) {
        return 1 + (pEnchantmentLevel - 1)*11;
    }

    public int getMaxCost(int pEnchantmentLevel) {
        return getMinCost(pEnchantmentLevel) + 11;
    }

    public int getMaxLevel() {
        return 5;
    }

    public int getDamageProtection(int pLevel, DamageSource pSource) {
        if (pSource.isMagic() || pSource.isBypassMagic()) {

            return pLevel * 3;

        }
        else{
            return 0;
        }
    }

}
