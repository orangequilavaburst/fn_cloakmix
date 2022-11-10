package xyz.j8bit_forager.cloakmix.item.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ArmorMaterial;
import xyz.j8bit_forager.cloakmix.item.ModArmorTiers;

import java.util.Collections;
import java.util.List;

public enum SpectralCloakTypes {
    TEST("test", ModArmorTiers.CLOTH, Collections.emptyList());

    private final String suffix; // the texture name, basically
    private final ArmorMaterial armorMaterial; // the armor material
    private final List<EntityType<?>> entityCamo; // entities you're hidden from

    SpectralCloakTypes(String suffix, ArmorMaterial armorMaterial, List<EntityType<?>> entityCamo) {
        this.suffix = suffix;
        this.armorMaterial = armorMaterial;
        this.entityCamo = entityCamo;
    }

    public String getSuffix() {
        return suffix;
    }

    public ArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }

    public List<EntityType<?>> getEntityCamo() {
        return entityCamo;
    }
}
