package xyz.j8bit_forager.cloakmix.client;

import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;
import xyz.j8bit_forager.cloakmix.enchantment.ModEnchantments;
import xyz.j8bit_forager.cloakmix.entity.ModEntityTypes;
import xyz.j8bit_forager.cloakmix.entity.client.armor.CloakArmorRenderer;
import xyz.j8bit_forager.cloakmix.entity.client.custom.renderer.SanguineDaggerRenderer;
import xyz.j8bit_forager.cloakmix.entity.client.custom.renderer.VorpalBladeRenderer;
import xyz.j8bit_forager.cloakmix.item.ModItems;
import xyz.j8bit_forager.cloakmix.item.custom.ModCloakItem;
import xyz.j8bit_forager.cloakmix.item.custom.ModDyeableCloakItem;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SuppressWarnings("removal")
    public static void setupRenderers() {

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BALD_CYPRESS_LEAVES.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BALD_CYPRESS_SAPLING.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_BALD_CYPRESS_SAPLING.get(), RenderType.cutoutMipped());

        EntityRenderers.register(ModEntityTypes.SANGUINE_DAGGER_PROJECTILE.get(), SanguineDaggerRenderer::new);
        EntityRenderers.register(ModEntityTypes.VORPAL_BLADE_PROJECTILE.get(), VorpalBladeRenderer::new);

    }

    @SubscribeEvent
    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
        //GeoArmorRenderer.registerArmorRenderer(ItemRalseiArmor.class, new RalseiHatRenderer());
        GeoArmorRenderer.registerArmorRenderer(ModCloakItem.class, new CloakArmorRenderer());
        GeoArmorRenderer.registerArmorRenderer(ModDyeableCloakItem.class, new CloakArmorRenderer());
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {

        event.register((state, worldIn, pos, tintIndex) -> {
            return (state != null && worldIn != null) ? BiomeColors.getAverageFoliageColor(worldIn, pos) : FoliageColor.getDefaultColor();
        }, ModBlocks.BALD_CYPRESS_LEAVES.get(), ModBlocks.BALD_CYPRESS_SAPLING.get(), ModBlocks.POTTED_BALD_CYPRESS_SAPLING.get());

        event.register((state, worldIn, pos, tintIndex) -> {
            return (state != null && worldIn != null) ? BiomeColors.getAverageGrassColor(worldIn, pos) : GrassColor.get(0.0f, 0.0f);
        }, ModBlocks.SPOOKY_GRASS_BLOCK.get());

    }

    @SubscribeEvent
    //@OnlyIn(Dist.CLIENT)
    public static void registerItemColours(RegisterColorHandlersEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();
        event.register((stack, tintIndex) -> {
            BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
            return blockColors.getColor(blockstate, (BlockAndTintGetter) null, (BlockPos) null, tintIndex);
        }, ModBlocks.BALD_CYPRESS_LEAVES.get(), ModBlocks.SPOOKY_GRASS_BLOCK.get());
        event.register((stack, tintIndex) -> {
            BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
            if (tintIndex == 1) {
                return blockColors.getColor(blockstate, (BlockAndTintGetter) null, (BlockPos) null, tintIndex);
            }
            return -1;
        }, ModBlocks.BALD_CYPRESS_SAPLING.get());
        event.register((stack, tintIndex) ->
        {
            return ((ModDyeableCloakItem) stack.getItem()).getColor(stack);
        }, ModItems.BASIC_CLOAK.get());
    }

    @SubscribeEvent
    public static void registerModels(ModelEvent.RegisterAdditional event){

        event.register(new ResourceLocation(CloakMix.MOD_ID, "item/vorpal_blade_projectile"));

    }

    // enchantment stuff

    /*@SubscribeEvent
    public void onRenderNameTag(RenderNameTagEvent event) {
        //temporary
        event.setResult(Event.Result.ALLOW);
    }*/

}
