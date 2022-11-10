package xyz.j8bit_forager.cloakmix.entity.client.armor;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.item.custom.ModCloakItem;
import xyz.j8bit_forager.cloakmix.item.custom.SpectralCloakItem;

public class SpectralCloakArmorModel extends AnimatedGeoModel<SpectralCloakItem> {

    @Override
    public ResourceLocation getModelResource(SpectralCloakItem modCloakItem) {
        String result = "geo/cloaks/spectral_cloak_";
        result += modCloakItem.getCloakType().getSuffix();
        result += ".geo.json";
        return new ResourceLocation(CloakMix.MOD_ID, result);
    }

    @Override
    public ResourceLocation getTextureResource(SpectralCloakItem modCloakItem) {
        String result = "textures/model/armor/cloaks/spectral_cloak_";
        result += modCloakItem.getCloakType().getSuffix();
        result += ".png";
        return new ResourceLocation(CloakMix.MOD_ID, result);
    }

    @Override
    public ResourceLocation getAnimationResource(SpectralCloakItem modCloakItem) {
        return new ResourceLocation(CloakMix.MOD_ID, "animations/armor_animation.json");
    }

}