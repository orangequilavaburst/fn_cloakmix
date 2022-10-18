package xyz.j8bit_forager.cloakmix.entity.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.enchantment.ModEnchantments;
import xyz.j8bit_forager.cloakmix.item.custom.ModCloakItem;
import xyz.j8bit_forager.cloakmix.item.custom.ModDyeableCloakItem;

import java.util.Random;

import static net.minecraft.util.Mth.*;

@Mod.EventBusSubscriber(modid = CloakMix.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModForgeEvents {

    private static float rotAmount = 20.0f;
    private static float billowSpeed = -0.125f;
    private Random RNG = new Random();

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRenderLivingPre(RenderLivingEvent.Pre event) {

        if (event.getEntity() instanceof Player) {

            Player player = (Player)event.getEntity();
            HumanoidModel model = (HumanoidModel) event.getRenderer().getModel();

            if (player.isSprinting() && !player.isCrouching() && playerWearingCloak(player) && !player.isSwimming()) {

                event.getPoseStack().pushPose();
                //event.getPoseStack().mulPose(Vector3f.ZP.rotationDegrees(180.0F));


                float yRot = event.getEntity().yBodyRotO + (event.getEntity().yBodyRot - event.getEntity().yBodyRotO) * event.getPartialTick();
                double transAmount = -0.25;

                model.getHead().setRotation(model.getHead().xRot, model.getHead().yRot - rotAmount, model.getHead().zRot);

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
            HumanoidModel model = (HumanoidModel) event.getRenderer().getModel();

            if (player.isSprinting() && !player.isCrouching() && playerWearingCloak(player) && !player.isSwimming()) {

                event.getPoseStack().popPose();

            }

        }

    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onPlayerTick(TickEvent.PlayerTickEvent event){

        // billowing

        ItemStack playerHelmet = event.player.getItemBySlot(EquipmentSlot.CHEST);

        if (!playerHelmet.isEmpty()){

            if (EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.BILLOWING.get(), playerHelmet) > 0) {

                //CloakMix.LOGGER.info("TRY TO BILLOW!!");

                if (event.player.getDeltaMovement().y <= billowSpeed) {

                    //CloakMix.LOGGER.info("Fall Distance: " + event.player.fallDistance);

                    if (Minecraft.getInstance().options.keyJump.isDown()) {

                        event.player.fallDistance = 0.0f;

                        event.player.setDeltaMovement(event.player.getDeltaMovement().x(), billowSpeed, event.player.getDeltaMovement().z());

                        event.player.level.addParticle(ParticleTypes.CLOUD, event.player.getX(), event.player.getY(), event.player.getZ(),
                                nextDouble(RandomSource.create(), -1.0, 1.0) / 10.0,
                                0.0f,
                                nextDouble(RandomSource.create(), -1.0, 1.0) / 10.0);

                    }

                }

            }

        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRenderTag(RenderNameTagEvent event){

        if (event.getEntity() instanceof Player){

            ItemStack playerHelmet = ((Player)event.getEntity()).getItemBySlot(EquipmentSlot.CHEST);

            if (!playerHelmet.isEmpty()) {

                if (EnchantmentHelper.getTagEnchantmentLevel(ModEnchantments.ANONYMITY.get(), playerHelmet) > 0) {

                    String replaceText = "";
                    for (int i = 0; i < event.getContent().getString().length(); i++){
                        replaceText += "?";
                    }

                    //CloakMix.LOGGER.info("Name: " + replaceText);

                    event.setContent(Component.literal(replaceText));

                }

                //event.setResult(Event.Result.DEFAULT);

            }

        }

    }

    private boolean playerWearingCloak(Player p){

        return (p.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ModCloakItem
        || p.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ModDyeableCloakItem);

    }

}
