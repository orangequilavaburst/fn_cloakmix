package xyz.j8bit_forager.cloakmix.entity.client.custom.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.entity.client.custom.SanguineDaggerProjectile;
import xyz.j8bit_forager.cloakmix.entity.client.custom.VorpalBladeProjectile;

public class VorpalBladeRenderer extends EntityRenderer<VorpalBladeProjectile> {

    private final ItemRenderer itemRenderer;

    public VorpalBladeRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(VorpalBladeProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot()) + 90.0F - pEntity.tickCount * 30.0f));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBuffer(pBuffer, RenderType.itemEntityTranslucentCull(this.getTextureLocation(pEntity)), true, false);
        this.itemRenderer.renderModelLists(
                Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(CloakMix.MOD_ID, "item/vorpal_blade_projectile")),
                ItemStack.EMPTY, pPackedLight, 0, pPoseStack, vertexconsumer);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(VorpalBladeProjectile pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
