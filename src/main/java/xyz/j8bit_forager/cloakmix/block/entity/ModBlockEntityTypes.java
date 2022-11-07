package xyz.j8bit_forager.cloakmix.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CloakMix.MOD_ID);

    public static final RegistryObject<BlockEntityType<SpectralLoomBlockEntity>> SPECTRAL_LOOM_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("spectral_loom", ()-> BlockEntityType.Builder.of(SpectralLoomBlockEntity::new,
                    ModBlocks.SPECTRAL_LOOM.get()).build(null));

    public static void register(IEventBus eventBus){

        BLOCK_ENTITY_TYPES.register(eventBus);

    }

}
