package xyz.j8bit_forager.cloakmix.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.enchantment.ModEnchantments;

public class AlteredSightGeoLayer extends GeoLayerRenderer {

    private IGeoRenderer<?> parent;


    @SuppressWarnings("unchecked")
    public AlteredSightGeoLayer(IGeoRenderer<?> parent) {
        super(parent);
        this.parent = parent;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        LocalPlayer player = Minecraft.getInstance().player;
        ItemStack playerHelmet = player.getItemBySlot(EquipmentSlot.HEAD);

        if (entity.isInvisible()) {
            if (!playerHelmet.isEmpty()) {
                if (EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.ALTERED_SIGHT.get(), playerHelmet) > 0) {
                    RenderType cameo =  ModRenderTypes.alteredSight(new ResourceLocation(CloakMix.MOD_ID, "textures/environment/altered_sight.png"));
                    VertexConsumer ivertexbuilder = bufferIn.getBuffer(cameo);
                    float alpha = 1.0F;
                    matrixStackIn.pushPose();
                    /*this.getEntityModel().getModel(this.getEntityModel().getModelLocation(entity)*/
                    this.getRenderer().render(this.getEntityModel().getModel(this.getEntityModel().getModelResource(entity)), entity, partialTicks, cameo, matrixStackIn, bufferIn,
                            bufferIn.getBuffer(cameo), packedLightIn, GeoEntityRenderer.getPackedOverlay((LivingEntity) entity, 0), 1f, 1f, 1f, alpha);
                    matrixStackIn.popPose();
                }
            }
        }

    }

}
