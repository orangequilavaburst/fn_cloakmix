package xyz.j8bit_forager.cloakmix.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, CloakMix.MOD_ID);

    public static final RegistryObject<MenuType<SpectralLoomMenu>> SPECTRAL_LOOM_MENU = registerMenuType(SpectralLoomMenu::new, "spectral_loom_menu");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register (IEventBus eventBus){

        MENU_TYPES.register(eventBus);

    }

}
