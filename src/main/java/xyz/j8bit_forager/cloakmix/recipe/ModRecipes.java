package xyz.j8bit_forager.cloakmix.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.cloakmix.CloakMix;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CloakMix.MOD_ID);

    public static final RegistryObject<RecipeSerializer<SpectralLoomRecipe>> SPECTRAL_LOOM_SERIALIZER =
            SERIALIZERS.register("spectral_loom", () -> SpectralLoomRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }

}
