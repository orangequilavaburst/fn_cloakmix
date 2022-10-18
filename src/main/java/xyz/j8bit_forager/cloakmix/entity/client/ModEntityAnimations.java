package xyz.j8bit_forager.cloakmix.entity.client;

import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.item.custom.ModCloakItem;
import xyz.j8bit_forager.cloakmix.item.custom.ModDyeableCloakItem;

import static net.minecraft.util.Mth.cos;
import static net.minecraft.util.Mth.sin;

@Mod.EventBusSubscriber(modid = CloakMix.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEntityAnimations {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRenderLivingPre(RenderLivingEvent.Pre event) {

        if (event.getEntity() instanceof Player) {

            Player player = (Player)event.getEntity();
            PlayerRenderer renderer = (PlayerRenderer) event.getRenderer();

            if (player.isSprinting() && !player.isCrouching() && playerWearingCloak(player)) {

                event.getPoseStack().pushPose();
                //event.getPoseStack().mulPose(Vector3f.ZP.rotationDegrees(180.0F));

                float yRot = event.getEntity().yBodyRotO + (event.getEntity().yBodyRot - event.getEntity().yBodyRotO) * event.getPartialTick();
                float rotAmount = 20.0f;
                double transAmount = -0.25;

                event.getPoseStack().mulPose(Vector3f.ZP.rotationDegrees(rotAmount * sin( (float) Math.toRadians((double) yRot))));
                event.getPoseStack().mulPose(Vector3f.XP.rotationDegrees(rotAmount * cos( (float) Math.toRadians((double) yRot))));
                event.getPoseStack().translate(transAmount * cos( (float) Math.toRadians((double) yRot + 90.0)),
                        0.0, transAmount * sin( (float) Math.toRadians((double) yRot + 90.0)));

                //event.getPoseStack().popPose();

            }

        }

    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRenderLivingPost(RenderLivingEvent.Post event) {

        if (event.getEntity() instanceof Player) {

            Player player = (Player)event.getEntity();
            PlayerRenderer renderer = (PlayerRenderer) event.getRenderer();

            if (player.isSprinting() && !player.isCrouching() && playerWearingCloak(player)) {

                event.getPoseStack().popPose();

            }

        }

    }

    private boolean playerWearingCloak(Player p){

        return (p.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ModCloakItem
        || p.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ModDyeableCloakItem);

    }

}
