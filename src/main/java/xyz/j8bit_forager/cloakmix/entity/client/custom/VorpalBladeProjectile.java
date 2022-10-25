package xyz.j8bit_forager.cloakmix.entity.client.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import xyz.j8bit_forager.cloakmix.entity.ModEntityTypes;

public class VorpalBladeProjectile extends ThrowableProjectile {

    private final float attackDamage;

    private float spinAngle;
    private float spinSpeed;

    public VorpalBladeProjectile(EntityType<VorpalBladeProjectile> entityType, Level world) {
        super(entityType, world);
        this.attackDamage = 1.0f;
        this.setBoundingBox(this.getBoundingBox().inflate(-0.5));

        resetAngles(30.0f);
    }

    public VorpalBladeProjectile(EntityType<VorpalBladeProjectile> entityType, double x, double y, double z, Level world) {
        super(entityType, x, y, z, world);
        this.attackDamage = 1.0f;
        this.setBoundingBox(this.getBoundingBox().inflate(-0.5));

        resetAngles(30.0f);
    }

    public VorpalBladeProjectile(EntityType<VorpalBladeProjectile> entityType, LivingEntity shooter, Level world) {
        super(entityType, shooter, world);
        this.attackDamage = 1.0f;
        this.setBoundingBox(this.getBoundingBox().inflate(-0.5));

        resetAngles(30.0f);
    }

    public VorpalBladeProjectile(LivingEntity shooter, Level world, float attackDamage) {
        super(ModEntityTypes.VORPAL_BLADE_PROJECTILE.get(), shooter, world);
        this.attackDamage = attackDamage;

        resetAngles(30.0f);
    }

    @Override
    public void tick() {
        this.baseTick();

        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (hitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)hitresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockpos, blockstate, this, (TheEndGatewayBlockEntity)blockentity);
                }

                flag = true;
            }
        }

        if (hitresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        spinAngle = lerpRotation(spinAngle, spinAngle + spinSpeed);

        Vec3 dV = this.getDeltaMovement();
        double distV = dV.horizontalDistance();
        this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(dV.y, distV) * (double)(180F / (float)Math.PI))));
        this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(dV.x, dV.z) * (double)(180F / (float)Math.PI)) + spinAngle));

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;

        float f = 1.001f;
        this.setDeltaMovement(vec3.scale(f));

        this.setPos(d2, d0, d1);

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
    public boolean isInWater() {
        return false;
    }

    private void resetAngles(float speed){

        this.spinAngle = 0.0f;
        this.spinSpeed = speed;

    }
}
