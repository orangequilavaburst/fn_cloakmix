package xyz.j8bit_forager.cloakmix.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import xyz.j8bit_forager.cloakmix.entity.client.custom.SanguineDaggerProjectile;
import xyz.j8bit_forager.cloakmix.entity.client.custom.VorpalBladeProjectile;
import xyz.j8bit_forager.cloakmix.item.ModCreativeModeTab;

public class ModVorpalBlade extends Item {

    private final float attackDamage;
    private final float attackSpeed;
    private final Item repairItem;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public ModVorpalBlade(Properties pProperties, float attackDamage, float attackSpeed, Item repairItem) {
        super(pProperties);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.repairItem = repairItem;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) this.attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
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

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        boolean canSummon = true;
        if (entity instanceof Player player){

            if (player.getAttackStrengthScale(1.0f) < 0.95f){

                canSummon = false;

            }

        }
        if (!entity.level.isClientSide) {
            if (canSummon) {

                VorpalBladeProjectile blade = new VorpalBladeProjectile(entity, entity.getLevel(), this.attackDamage);
                blade.shootFromRotation(blade, entity.getXRot(), entity.getYRot(), 0.0F, 1.25F, 0.0F);
                entity.level.addFreshEntity(blade);

            }
        }
        return super.onEntitySwing(stack, entity);
    }
}
