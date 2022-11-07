package xyz.j8bit_forager.cloakmix;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;
import xyz.j8bit_forager.cloakmix.block.entity.ModBlockEntityTypes;
import xyz.j8bit_forager.cloakmix.client.ModClientEvents;
import xyz.j8bit_forager.cloakmix.enchantment.ModEnchantments;
import xyz.j8bit_forager.cloakmix.entity.ModEntityTypes;
import xyz.j8bit_forager.cloakmix.entity.client.ModForgeEvents;
import xyz.j8bit_forager.cloakmix.item.ModCreativeModeTab;
import xyz.j8bit_forager.cloakmix.item.ModItemProperties;
import xyz.j8bit_forager.cloakmix.item.ModItems;
import xyz.j8bit_forager.cloakmix.messages.ModMessages;
import xyz.j8bit_forager.cloakmix.screen.ModMenuTypes;
import xyz.j8bit_forager.cloakmix.screen.SpectralLoomScreen;
import xyz.j8bit_forager.cloakmix.world.feature.ModConfiguredFeatures;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CloakMix.MOD_ID)
public class CloakMix
{
    public static final String MOD_ID = "cloakmix";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CloakMix()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEnchantments.register(modEventBus);
        ModConfiguredFeatures.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

        ModMessages.register();

        ((FlowerPotBlock)Blocks.FLOWER_POT).addPlant(ModBlocks.BALD_CYPRESS_SAPLING.getId(), ModBlocks.POTTED_BALD_CYPRESS_SAPLING);

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            MinecraftForge.EVENT_BUS.register(ModClientEvents.class);
            ModClientEvents.setupRenderers();
            MinecraftForge.EVENT_BUS.register(new ModForgeEvents());

            ModCreativeModeTab.CLOAKMIX_ITEMS_TAB.setEnchantmentCategories(ModEnchantments.CLOAK);

            ModItemProperties.addCustomItemProperties();

            MenuScreens.register(ModMenuTypes.SPECTRAL_LOOM_MENU.get(), SpectralLoomScreen::new);

        }
    }
}
