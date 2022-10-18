package xyz.j8bit_forager.cloakmix.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.enchantment.ModEnchantments;

public class AlteredSightLayer extends RenderLayer {

    private RenderLayerParent parent;

    public AlteredSightLayer(RenderLayerParent parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        LocalPlayer player = Minecraft.getInstance().player;
        ItemStack playerHelmet = player.getItemBySlot(EquipmentSlot.CHEST);

        if (entity.isInvisible()) {
            if (!playerHelmet.isEmpty()) {
                if (EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.ALTERED_SIGHT.get(), playerHelmet) > 0) {
                    VertexConsumer ivertexbuilder = bufferIn.getBuffer(ModRenderTypes.alteredSight(new ResourceLocation(CloakMix.MOD_ID, "textures/environment/altered_sight.png")));
                    float alpha = 1.0F;
                    matrixStackIn.pushPose();
                    this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords((LivingEntity) entity, 0), 1, 1, 1, alpha);
                    matrixStackIn.popPose();
                }
            }
        }

    }



}
