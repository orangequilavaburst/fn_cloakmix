package xyz.j8bit_forager.cloakmix.entity.client.custom;

import io.netty.buffer.ByteBuf;
import net.minecraft.Util;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.entity.ModEntityTypes;
import xyz.j8bit_forager.cloakmix.item.ModItems;
import xyz.j8bit_forager.cloakmix.item.custom.ModSamguineDagger;
import xyz.j8bit_forager.cloakmix.messages.ModMessages;
import xyz.j8bit_forager.cloakmix.messages.packet.ParticleSpawnPacket;

import java.nio.ByteBuffer;

public class SanguineDaggerProjectile extends ThrowableProjectile {

    private final float attackDamage;
    private Item itemType;

    public SanguineDaggerProjectile(EntityType<SanguineDaggerProjectile> entityType, Level world) {
        super(entityType, world);
        this.attackDamage = 1.0f;
        this.itemType = getDefaultItem();
    }

    public SanguineDaggerProjectile(EntityType<SanguineDaggerProjectile> entityType, double x, double y, double z, Level world) {
        super(entityType, x, y, z, world);
        this.attackDamage = 1.0f;
        this.itemType = getDefaultItem();
    }

    public SanguineDaggerProjectile(EntityType<SanguineDaggerProjectile> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world);
        this.attackDamage = 1.0f;
        this.itemType = getDefaultItem();
    }

    public SanguineDaggerProjectile(LivingEntity shooter, Level world, float attackDamage, Item itemType) {
        super(ModEntityTypes.SANGUINE_DAGGER_PROJECTILE.get(), shooter, world);
        this.attackDamage = attackDamage;
        this.itemType = itemType;
    }

    protected Item getDefaultItem() {
        return ModItems.SANGUINE_DAGGER.get();
    }

    protected ItemStack getItemRaw() {
        return new ItemStack(this.itemType);
    }

    public void setItem(ItemStack pStack) {
        if (!pStack.is(this.getDefaultItem()) || pStack.hasTag()) {
            this.itemType = pStack.getItem();
        }

    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = new ItemStack(this.itemType);
        return (ParticleOptions)(new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    @Override
    protected void defineSynchedData() {

    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(particleoptions,
                        this.getX(), this.getY(), this.getZ(),
                        this.level.random.nextDouble() * 0.1 - 0.05, this.level.random.nextDouble() * 0.1, this.level.random.nextDouble() * 0.1 - 0.05);
            }
        }

    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if (pResult.getEntity() != this.getOwner()) {
            Entity entity = pResult.getEntity();
            Level level = entity.getCommandSenderWorld();
            boolean fromBehind = ModSamguineDagger.isLookingBehindTarget((LivingEntity) entity, this.position());
            float damage = this.attackDamage * (fromBehind ? 2 : 1);
            if (!level.isClientSide()) {
                entity.hurt(DamageSource.thrown(this, this.getOwner()), damage);
                if (this.getOwner() instanceof LivingEntity livingEntity) {
                    livingEntity.heal(this.attackDamage);

                    //ModMessages.sendToServer(new ParticleSpawnPacket(entity.position(), this.getOwner().position().add(0.0, this.getOwner().getBbHeight() / 2.0, 0.0), ParticleTypes.HEART));
                    ModMessages.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this),
                            new ParticleSpawnPacket(entity.position(), this.getOwner().position().add(0.0, this.getOwner().getBbHeight() / 2.0, 0.0), ParticleTypes.HEART));

                }
            }
        }

    }

    protected float getGravity() {
        return 0.075F;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
