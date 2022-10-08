package xyz.j8bit_forager.cloakmix.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SuppressWarnings("removal")
    public static void setupRenderers() {

        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BALD_CYPRESS_LEAVES.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BALD_CYPRESS_SAPLING.get(), RenderType.cutout());

    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event){

        event.register((state, worldIn, pos, tintIndex) -> {
            return (state != null && worldIn != null) ? BiomeColors.getAverageFoliageColor(worldIn, pos) : FoliageColor.getDefaultColor();
        }, ModBlocks.BALD_CYPRESS_LEAVES.get());

        event.register((state, worldIn, pos, tintIndex) -> {
            return (state != null && worldIn != null) ? BiomeColors.getAverageGrassColor(worldIn, pos) : GrassColor.get(0.0f, 0.0f);
        }, ModBlocks.SPOOKY_GRASS_BLOCK.get());

    }

    @SubscribeEvent
    public static void registerItemColours(RegisterColorHandlersEvent.Item event) {
        BlockColors blockColors = event.getBlockColors();
        event.register((stack, tintIndex) -> {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
            return blockColors.getColor(blockstate, (BlockAndTintGetter)null, (BlockPos)null, tintIndex);
        }, ModBlocks.BALD_CYPRESS_LEAVES.get(), ModBlocks.SPOOKY_GRASS_BLOCK.get());
    }

}
