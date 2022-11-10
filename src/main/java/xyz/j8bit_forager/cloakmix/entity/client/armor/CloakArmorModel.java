package xyz.j8bit_forager.cloakmix.entity.client.armor;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.item.custom.ModCloakItem;

public class CloakArmorModel extends AnimatedGeoModel<ModCloakItem> {

    @Override
    public ResourceLocation getModelResource(ModCloakItem modCloakItem) {
        return new ResourceLocation(CloakMix.MOD_ID, "geo/cloaks/base_cloak.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ModCloakItem modCloakItem) {
        return new ResourceLocation(CloakMix.MOD_ID, "textures/model/armor/cloaks/base_cloak.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ModCloakItem modCloakItem) {
        return new ResourceLocation(CloakMix.MOD_ID, "animations/armor_animation.json");
    }

}