package xyz.j8bit_forager.cloakmix.entity.client.custom;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fluids.FluidType;
import xyz.j8bit_forager.cloakmix.entity.ModEntityTypes;

public class VorpalBladeProjectile extends ThrowableProjectile {

    private final float attackDamage;

    public VorpalBladeProjectile(EntityType<VorpalBladeProjectile> entityType, Level world) {
        super(entityType, world);
        this.attackDamage = 1.0f;
    }

    public VorpalBladeProjectile(EntityType<VorpalBladeProjectile> entityType, double x, double y, double z, Level world) {
        super(entityType, x, y, z, world);
        this.attackDamage = 1.0f;
    }

    public VorpalBladeProjectile(EntityType<VorpalBladeProjectile> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world);
        this.attackDamage = 1.0f;
    }

    public VorpalBladeProjectile(LivingEntity shooter, Level world, float attackDamage) {
        super(ModEntityTypes.VORPAL_BLADE_PROJECTILE.get(), shooter, world);
        this.attackDamage = attackDamage;
    }

    private ParticleOptions getParticle() {
        return ParticleTypes.PORTAL;
    }

    @Override
    protected void defineSynchedData() {

    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level.isClientSide) {
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
            float damage = this.attackDamage;
            if (!level.isClientSide()) {
                entity.hurt(DamageSource.thrown(this, this.getOwner()), damage);
                entity.setDeltaMovement(this.getDeltaMovement().normalize().scale(1.1));
            }
        }

    }

    protected float getGravity() {
        return 0.0F;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public double getFluidMotionScale(FluidType type) {
        return 2.0;
    }
}
