package xyz.j8bit_forager.cloakmix.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;
import xyz.j8bit_forager.cloakmix.recipe.SpectralLoomRecipe;

public class SpectralLoomRecipeCategory implements IRecipeCategory<SpectralLoomRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(CloakMix.MOD_ID, "spectral_loom");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(CloakMix.MOD_ID, "textures/gui/spectral_loom_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public SpectralLoomRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SPECTRAL_LOOM.get()));
    }

    @Override
    public RecipeType<SpectralLoomRecipe> getRecipeType() {
        return JEICloakMixPlugin.SPECTRAL_LOOM_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Spectral Loom");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpectralLoomRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 17).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 30, 53).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 53).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 53).addIngredients(recipe.getIngredients().get(3));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 124, 35).addItemStack(recipe.getResultItem());
    }
}
