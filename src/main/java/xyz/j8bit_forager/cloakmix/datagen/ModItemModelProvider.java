package xyz.j8bit_forager.cloakmix.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import xyz.j8bit_forager.cloakmix.CloakMix;

public class ModItemModelProvider extends ItemModelProvider {


    public ModItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, CloakMix.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getDescriptionId(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(CloakMix.MOD_ID,"item/" + item.getDescriptionId()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(item.getDescriptionId(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(CloakMix.MOD_ID,"item/" + item.getDescriptionId()));
    }

}
