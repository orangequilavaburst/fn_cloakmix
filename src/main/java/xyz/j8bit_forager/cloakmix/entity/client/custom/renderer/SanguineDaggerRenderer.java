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
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.j8bit_forager.cloakmix.entity.client.custom.SanguineDaggerProjectile;
import xyz.j8bit_forager.cloakmix.item.ModItems;

@OnlyIn(Dist.CLIENT)
public class SanguineDaggerRenderer extends EntityRenderer<SanguineDaggerProjectile> {
    private final float scale;
    private final boolean fullBright;
    private final Item itemType;
    private final ItemRenderer itemRenderer;
    public SanguineDaggerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.scale = 1.0f;
        this.fullBright = false;
        this.itemType = ModItems.SANGUINE_DAGGER.get();
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    protected SanguineDaggerRenderer(EntityRendererProvider.Context pContext, float scale, boolean fullBright, Item itemType) {
        super(pContext);
        this.scale = scale;
        this.fullBright = fullBright;
        this.itemType = itemType;
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(SanguineDaggerProjectile pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        ItemStack itemStack = new ItemStack(this.itemType);
        pPoseStack.pushPose();
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot()) + 90.0F - pEntity.tickCount * 30.0f));
        //VertexConsumer vertexconsumer = ItemRenderer.getFoilBuffer(pBuffer, RenderType.itemEntityTranslucentCull(this.getTextureLocation(pEntity)), true, false);
        this.itemRenderer.render(itemStack, ItemTransforms.TransformType.NONE, false, pPoseStack, pBuffer, pPackedLight, 0, itemRenderer.getModel(itemStack, null, null, 0));
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SanguineDaggerProjectile pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }


}
