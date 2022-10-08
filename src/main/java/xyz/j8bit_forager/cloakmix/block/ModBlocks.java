package xyz.j8bit_forager.cloakmix.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.item.ModCreativeModeTab;
import xyz.j8bit_forager.cloakmix.item.ModItems;
import xyz.j8bit_forager.cloakmix.world.feature.tree.BaldCypressTreeGrower;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CloakMix.MOD_ID);


    // basic blocks

    public static final RegistryObject<Block> SPOOKY_SOIL = registerBlock("spooky_soil",
            () -> new Block(BlockBehaviour.Properties
                    .of(Material.DIRT, MaterialColor.COLOR_GRAY)
                    .strength(0.5F)
                    .sound(SoundType.GRAVEL)),
            ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> SPOOKY_GRASS_BLOCK = registerBlock("spooky_grass_block",
            () -> new GrassBlock(BlockBehaviour.Properties
                    .copy(Blocks.GRASS_BLOCK)),
            ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> BALD_CYPRESS_WOOD = registerBlock("bald_cypress_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties
                    .copy(Blocks.OAK_WOOD)), ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> STRIPPED_BALD_CYPRESS_WOOD = registerBlock("stripped_bald_cypress_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties
                    .copy(Blocks.STRIPPED_OAK_WOOD)), ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> BALD_CYPRESS_LOG = registerBlock("bald_cypress_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties
                    .copy(Blocks.OAK_LOG)), ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> STRIPPED_BALD_CYPRESS_LOG = registerBlock("stripped_bald_cypress_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties
                    .copy(Blocks.STRIPPED_OAK_LOG)), ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> BALD_CYPRESS_LEAVES = registerBlock("bald_cypress_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties
                    .copy(Blocks.OAK_LEAVES)
            ){

                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }
                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }
            },
            ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> BALD_CYPRESS_PLANKS = registerBlock("bald_cypress_planks",
            () -> new Block(BlockBehaviour.Properties
                    .of(Material.WOOD, MaterialColor.WOOD)
                    .strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD)
            ){

                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }
                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }
            },
            ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> BALD_CYPRESS_SAPLING = registerBlock("bald_cypress_sapling",
            () -> new SaplingBlock(new BaldCypressTreeGrower(),BlockBehaviour.Properties
                    .copy(Blocks.OAK_SAPLING)
            ),
            ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> POTTED_BALD_CYPRESS_SAPLING = registerBlockWithoutBlockItem("potted_bald_cypress_sapling",
            () -> new FlowerPotBlock(null, ModBlocks.BALD_CYPRESS_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.POTTED_ORANGE_TULIP).noOcclusion()));

    //differently shaped blocks

    public static final RegistryObject<Block> BALD_CYPRESS_SLAB = registerBlock("bald_cypress_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(BALD_CYPRESS_PLANKS.get())),
            ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> BALD_CYPRESS_STAIRS = registerBlock("bald_cypress_stairs",
            () -> new StairBlock(() -> ModBlocks.BALD_CYPRESS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(BALD_CYPRESS_PLANKS.get())),
            ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> BALD_CYPRESS_FENCE = registerBlock("bald_cypress_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(BALD_CYPRESS_PLANKS.get())),
            ModCreativeModeTab.CLOAKMIX_TAB);

    public static final RegistryObject<Block> BALD_CYPRESS_FENCE_GATE = registerBlock("bald_cypress_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(BALD_CYPRESS_PLANKS.get())),
            ModCreativeModeTab.CLOAKMIX_TAB);

    //register

    public static void register(IEventBus eventBus){

        BLOCKS.register(eventBus);

    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){

        RegistryObject<T> result = BLOCKS.register(name, block);
        registerBlockItem(name, result, tab);

        return result;

    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block){

        RegistryObject<T> result = BLOCKS.register(name, block);

        return result;

    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){

        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));

    }

}
