package xyz.j8bit_forager.cloakmix.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.j8bit_forager.cloakmix.item.custom.ModCloakItem;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class ModLivingEntityMixin {

    @Shadow protected abstract float getJumpPower();

    @Inject(method = "getFrictionInfluencedSpeed", at = @At("TAIL"), cancellable = true)
    public void alterFriction(float pFriction, CallbackInfoReturnable<Float> cir){

        LivingEntity livingEntity = (LivingEntity)(Object)this;

        if (livingEntity instanceof Player){

            ItemStack chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST);

            if (chestplate != null && chestplate.getItem() instanceof ModCloakItem) {

                float returnSpeed = livingEntity.getSpeed();

                if (livingEntity.isOnGround()) {

                    returnSpeed *= ((livingEntity.getDeltaMovement().length() < 0.1f)) ? pFriction : 1.0f;

                } else {

                    returnSpeed *= 0.5f;

                }

                cir.setReturnValue(returnSpeed);

                //cir.setReturnValue(livingEntity.isOnGround() ? livingEntity.getSpeed() * (0.21600002F / (pFriction)) : livingEntity.getSpeed() * pFriction);
            }

        }

    }

    @Inject(method = "getJumpPower", at = @At("HEAD"), cancellable = true)
    public void alterJumpPower(CallbackInfoReturnable<Float> cir){

        LivingEntity livingEntity = (LivingEntity)(Object)this;

        if (livingEntity instanceof Player) {

            ItemStack chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST);

            if (chestplate != null && chestplate.getItem() instanceof ModCloakItem) {

                cir.setReturnValue(0.43F + (float)livingEntity.getDeltaMovement().length()/5.0f);

            }

        }

    }

}
