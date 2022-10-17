package xyz.j8bit_forager.cloakmix.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class ModDyeableCloakItem extends ModCloakItem implements DyeableLeatherItem {

    public ModDyeableCloakItem(ArmorMaterial material, Properties settings) {
        super(material, settings);
    }

    @Override
    public int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99)  ? compoundtag.getInt("color") : 11049144;
    }

}
