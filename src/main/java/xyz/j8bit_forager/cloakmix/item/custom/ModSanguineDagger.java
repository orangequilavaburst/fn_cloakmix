package xyz.j8bit_forager.cloakmix.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import xyz.j8bit_forager.cloakmix.entity.client.custom.SanguineDaggerProjectile;

public class ModSanguineDagger extends Item implements Vanishable {
    private final float attackDamage;
    private final float attackSpeed;
    private final int chargeTime;
    private final Item repairItem;
    /**
     * Modifiers applied when the item is in the mainhand of a user.
     */
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ModSanguineDagger(Properties pProperties, float attackDamage, float attackSpeed, int chargeTime, Item repairItem) {

        super(pProperties);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.chargeTime = chargeTime;
        this.repairItem = repairItem;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) this.attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();

    }

    public int getChargeTime(){
        return this.chargeTime;
    }

    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.is(this.repairItem);
    }

    public boolean isCorrectToolForDrops(BlockState pBlock) {
        return pBlock.is(Blocks.COBWEB);
    }

    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    public boolean isEnchantable(ItemStack pStack) {
        return false;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack pStack) {
        return 15000;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);

        if (count == this.getUseDuration(stack) - this.chargeTime){

            player.playSound(SoundEvents.NOTE_BLOCK_PLING, 1.0f, 2.0f);

        }

        //CloakMix.LOGGER.info("Use time: " + ((float)(this.getUseDuration(stack) - player.getUseItemRemainingTicks()) / this.getChargeTime()));

    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {

            //CloakMix.LOGGER.info(pTimeLeft + " | " + (this.getUseDuration(pStack) - this.chargeTime));
            if (pTimeLeft > this.getUseDuration(pStack) - this.chargeTime) return;

            if (!pLevel.isClientSide) {

                int num = 3;
                float spread = 20.0f;

                for (int i = -num/2; i <= num/2; i++){

                    SanguineDaggerProjectile dagger = new SanguineDaggerProjectile(pEntityLiving, pLevel, this.attackDamage, this.asItem());
                    dagger.setItem(new ItemStack(this.asItem()));
                    dagger.shootFromRotation(player, player.getXRot(), player.getYRot() + spread*i, 0.0F, 2.0F, 0.0F);
                    pLevel.addFreshEntity(dagger);

                }

                pStack.hurtAndBreak(2, player, (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                });
            }

            pLevel.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);

            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Level level = pTarget.getCommandSenderWorld();
        if (!level.isClientSide) {
            if (isLookingBehindTarget(pTarget, pAttacker.position())) {
                pTarget.hurt((pAttacker instanceof Player) ? DamageSource.playerAttack((Player) pAttacker) : DamageSource.mobAttack(pAttacker), this.attackDamage);
                pAttacker.heal(this.attackDamage);
                pTarget.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0f, 1.0f);
                pTarget.playSound(SoundEvents.ARROW_HIT_PLAYER, 1.0f, 1.0f); // replace with unique sound

            } else {
                pStack.hurtAndBreak(1, pAttacker, (p_43296_) -> {
                    p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }
        }
        return true;
    }

    /**
     * Determines whether the attacker is facing a 90-100 degree cone behind the target's looking direction.
     * Shamelessly borrowed from Farmer's Delight
     */
    public static boolean isLookingBehindTarget(LivingEntity target, Vec3 attackerLocation) {
        if (attackerLocation != null) {
            Vec3 lookingVector = target.getViewVector(1.0F);
            Vec3 attackAngleVector = attackerLocation.subtract(target.position()).normalize();
            attackAngleVector = new Vec3(attackAngleVector.x, 0.0D, attackAngleVector.z);
            return attackAngleVector.dot(lookingVector) < -0.5D;
        }
        return false;
    }



}
