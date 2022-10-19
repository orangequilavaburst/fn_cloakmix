package xyz.j8bit_forager.cloakmix.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.j8bit_forager.cloakmix.CloakMix;
@OnlyIn(Dist.CLIENT)
public class AnonymityLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private final RenderLayerParent<T, M> parent;
    EntityModel<T> entityModel;

    public AnonymityLayer(RenderLayerParent pRenderer) {
        super(pRenderer);
        this.parent = pRenderer;
        entityModel = pRenderer.getModel();
    }

    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

        CloakMix.LOGGER.info("TRYING TO CALL ANONYMITY FROM " + pLivingEntity.getType().toString());

        if (pLivingEntity instanceof Player player) {
            float f = (float)pLivingEntity.tickCount + pPartialTicks;
            EntityModel<T> entitymodel = this.model();
            entitymodel.prepareMobModel(pLivingEntity, pLimbSwing, pLimbSwingAmount, pPartialTicks);
            this.getParentModel().copyPropertiesTo(entitymodel);
            VertexConsumer vertexconsumer = pBuffer.getBuffer(ModRenderTypes.anonymityLayer(this.getTextureLocation(), this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
            entitymodel.setupAnim(pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
            entitymodel.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

            CloakMix.LOGGER.info("render the anonymous!");
        }
    }

    protected float xOffset(float p_116968_){

        return p_116968_;

    }

    protected ResourceLocation getTextureLocation(){

        return new ResourceLocation(CloakMix.MOD_ID, "textures/environment/anonymity.png");

    }

    protected EntityModel<T> model(){

        return entityModel;

    }
}