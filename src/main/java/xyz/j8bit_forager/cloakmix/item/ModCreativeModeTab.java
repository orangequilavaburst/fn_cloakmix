package xyz.j8bit_forager.cloakmix.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {

    public static final CreativeModeTab CLOAKMIX_TAB = new CreativeModeTab("cloakmix_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.REAPER_CLOTH.get());
        }
    };

}
