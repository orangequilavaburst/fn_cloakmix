package xyz.j8bit_forager.cloakmix.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import xyz.j8bit_forager.cloakmix.item.custom.ModSanguineDagger;

public class ModItemProperties {

    public static void addCustomItemProperties() {
        makeSanguineDagger((ModSanguineDagger) ModItems.SANGUINE_DAGGER.get());

        ItemProperties.register(ModItems.VORPAL_BLADE.get(), new ResourceLocation("swingtime"),
                (p_174635_, p_174636_, p_174637_, p_174638_) -> {
                    if (p_174637_ == null) {
                        return 0.0F;
                    } else {
                        return p_174637_.getAttackAnim(1.0f);
                    }
                });
    }

    // from Kaupenjoe
    private static void makeBow(Item item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() -
                        p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }

    private static void makeSanguineDagger(ModSanguineDagger item) {
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() -
                        p_174637_.getUseItemRemainingTicks()) / item.getChargeTime();
            }
        });

        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
            return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
        });
    }
}
