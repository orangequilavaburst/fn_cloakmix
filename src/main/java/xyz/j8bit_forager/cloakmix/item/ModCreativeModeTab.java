package xyz.j8bit_forager.cloakmix.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;
import xyz.j8bit_forager.cloakmix.enchantment.ModEnchantments;

public class ModCreativeModeTab {

    public static final CreativeModeTab CLOAKMIX_ITEMS_TAB = new CreativeModeTab("cloakmix_item_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.REAPER_CLOTH.get());
        }

    };

    public static final CreativeModeTab CLOAKMIX_BLOCKS_TAB = new CreativeModeTab("cloakmix_block_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.SPOOKY_GRASS_BLOCK.get());
        }
    };

}
