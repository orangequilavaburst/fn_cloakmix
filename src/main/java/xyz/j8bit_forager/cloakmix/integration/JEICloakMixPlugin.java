package xyz.j8bit_forager.cloakmix.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.recipe.SpectralLoomRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEICloakMixPlugin implements IModPlugin {
    public static RecipeType<SpectralLoomRecipe> SPECTRAL_LOOM_TYPE =
            new RecipeType<>(SpectralLoomRecipeCategory.UID, SpectralLoomRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CloakMix.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                SpectralLoomRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<SpectralLoomRecipe> recipesSpectralLoom = rm.getAllRecipesFor(SpectralLoomRecipe.Type.INSTANCE);
        registration.addRecipes(SPECTRAL_LOOM_TYPE, recipesSpectralLoom);
    }
}
