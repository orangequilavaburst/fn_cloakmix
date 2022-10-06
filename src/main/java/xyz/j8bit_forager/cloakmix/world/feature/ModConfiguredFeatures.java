package xyz.j8bit_forager.cloakmix.world.feature;

import net.minecraft.core.Registry;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;

public class ModConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, CloakMix.MOD_ID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> BALD_CYPRESS =
            CONFIGURED_FEATURES.register("bald_cypress",
                    ()-> new ConfiguredFeature<>(
                            Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                                    BlockStateProvider.simple(ModBlocks.BALD_CYPRESS_LOG.get()),
                                    new BendingTrunkPlacer(4, 3, 5, 6, ConstantInt.of(3)),
                                    BlockStateProvider.simple(ModBlocks.BALD_CYPRESS_LEAVES.get()),
                                    new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                                    new TwoLayersFeatureSize(1, 0, 2)).build()));

    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURES.register(eventBus);
    }

}
