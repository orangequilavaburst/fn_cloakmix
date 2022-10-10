package xyz.j8bit_forager.cloakmix.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import xyz.j8bit_forager.cloakmix.block.ModBlocks;
import xyz.j8bit_forager.cloakmix.item.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        //super.buildCraftingRecipes(pFinishedRecipeConsumer);

        /*ShapedRecipeBuilder
        .shaped(itemResult, count)
        .define(char, item)
        .pattern()
        .pattern()
        .pattern()
        .unlockedBy(name, criteria)
         .save(pFinishedRecipeConsumer)*/

        ShapedRecipeBuilder.shaped(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .define('R', ModItems.REAPER_CLOTH.get())
                .pattern("RR")
                .pattern("RR")
                .unlockedBy("has_reaper_cloth", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.REAPER_CLOTH.get()).build()))
                .save(pFinishedRecipeConsumer);

        /* ShapelessRecipeBuilder
        .shapeless(itemResult, count)
        .requires(item)
        .unlockedBy(name, criteria)
        .save(pFinishedRecipeConsumer)
         */

        ShapelessRecipeBuilder.shapeless(Items.STRING, 3)
                .requires(ModItems.REAPER_CLOTH.get())
                .unlockedBy("has_reaper_cloth", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.REAPER_CLOTH.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_ORANGE.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.ORANGE_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_MAGENTA.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.MAGENTA_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_LIGHT_BLUE.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.LIGHT_BLUE_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_YELLOW.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.YELLOW_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_LIME.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.LIME_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_PINK.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.PINK_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_GRAY.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.GRAY_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_LIGHT_GRAY.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.LIGHT_GRAY_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_CYAN.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.CYAN_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_PURPLE.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.PURPLE_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_BLUE.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.BLUE_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_BROWN.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.BROWN_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_GREEN.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.GREEN_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_RED.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.RED_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_BLACK.get())
                .requires(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get())
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_spectral_patchwork_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.SPECTRAL_PATCHWORK_BLOCK_WHITE.get()).build()))
                .save(pFinishedRecipeConsumer);

        /* EXECUTE RUN DATA TO GENERATE THESE!!! */

    }
}
