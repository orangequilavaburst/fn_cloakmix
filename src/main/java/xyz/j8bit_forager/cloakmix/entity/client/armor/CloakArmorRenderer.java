package xyz.j8bit_forager.cloakmix.entity.client.armor;

import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import xyz.j8bit_forager.cloakmix.item.custom.ModCloakItem;

public class CloakArmorRenderer extends GeoArmorRenderer<ModCloakItem> {


    public CloakArmorRenderer() {
        super(new CloakArmorModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorLeftLeg";
        this.leftLegBone = "armorRightLeg";
        this.rightBootBone = "armorLeftBoot";
        this.leftBootBone = "armorRightBoot";

    }

    @Override
    public CloakArmorRenderer applySlot(EquipmentSlot slot) {
        this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelResource(this.currentArmorItem));
        IBone headBone = this.getAndHideBone(this.headBone);
        IBone bodyBone = this.getAndHideBone(this.bodyBone);
        IBone rightArmBone = this.getAndHideBone(this.rightArmBone);
        IBone leftArmBone = this.getAndHideBone(this.leftArmBone);
        IBone rightLegBone = this.getAndHideBone(this.rightLegBone);
        IBone leftLegBone = this.getAndHideBone(this.leftLegBone);
        IBone rightBootBone = this.getAndHideBone(this.rightBootBone);
        IBone leftBootBone = this.getAndHideBone(this.leftBootBone);
        switch (slot) {
            case HEAD:

                break;
            case CHEST:
                if (headBone != null) {
                    headBone.setHidden(false);
                }

                if (bodyBone != null) {
                    bodyBone.setHidden(false);
                }

                if (rightArmBone != null) {
                    rightArmBone.setHidden(false);
                }

                if (leftArmBone != null) {
                    leftArmBone.setHidden(false);
                }
                if (rightLegBone != null) {
                    rightLegBone.setHidden(false);
                }

                if (leftLegBone != null) {
                    leftLegBone.setHidden(false);
                }

                if (rightBootBone != null) {
                    rightBootBone.setHidden(false);
                }

                if (leftBootBone != null) {
                    leftBootBone.setHidden(false);
                }

                break;
            case LEGS:

                break;
            case FEET:

                break;
        }

        return this;
    }

}