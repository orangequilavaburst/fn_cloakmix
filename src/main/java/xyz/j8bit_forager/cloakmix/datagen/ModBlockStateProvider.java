package xyz.j8bit_forager.cloakmix.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {


    public ModBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, CloakMix.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_ORANGE.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_MAGENTA.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_LIGHT_BLUE.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_YELLOW.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_LIME.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_PINK.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_GRAY.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_LIGHT_GRAY.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_CYAN.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_PURPLE.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_BLUE.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_BROWN.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_GREEN.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_RED.get());
        simpleBlock(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_BLACK.get());

    }
}
