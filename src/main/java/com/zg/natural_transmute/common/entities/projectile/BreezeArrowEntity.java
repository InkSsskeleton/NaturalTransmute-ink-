package com.zg.natural_transmute.common.entities.projectile;

import com.zg.natural_transmute.registry.NTEntityTypes;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class BreezeArrowEntity extends AbstractArrow {

    public static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(Boolean.TRUE, Boolean.FALSE,
            Optional.of(1.5F), BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()));

    public BreezeArrowEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public BreezeArrowEntity(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(NTEntityTypes.BREEZE_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    public BreezeArrowEntity(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(NTEntityTypes.BREEZE_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    public void tick() {
        if (!this.hasBeenShot) {
            this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner());
            this.hasBeenShot = true;
        }

        if (!this.leftOwner) {
            this.leftOwner = this.checkLeftOwner();
        }

        this.baseTick();
        Vec3 vec3 = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / Mth.PI));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / Mth.PI));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        BlockPos blockPos = this.blockPosition();
        BlockState blockState = this.level().getBlockState(blockPos);
        if (!blockState.isAir()) {
            VoxelShape voxelShape = blockState.getCollisionShape(this.level(), blockPos);
            if (!voxelShape.isEmpty()) {
                Vec3 vec31 = this.position();
                for (AABB aabb : voxelShape.toAabbs()) {
                    if (aabb.move(blockPos).contains(vec31)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.shakeTime > 0) {
            this.shakeTime--;
        }

        if (this.isInWaterOrRain() || blockState.is(Blocks.POWDER_SNOW) || this.isInFluidType((fluidType, height) -> this.canFluidExtinguish(fluidType))) {
            this.clearFire();
        }

        if (this.inGround) {
            if (this.lastState != blockState && this.shouldFall()) {
                this.startFalling();
            } else if (!this.level().isClientSide) {
                this.tickDespawn();
            }

            this.inGroundTime++;
        } else {
            this.inGroundTime = 0;
            Vec3 vec32 = this.position();
            Vec3 vec33 = vec32.add(vec3);
            HitResult hitResult = this.level().clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hitResult.getType() != HitResult.Type.MISS) {
                vec33 = hitResult.getLocation();
            }

            while (!this.isRemoved()) {
                EntityHitResult entityHitResult = this.findHitEntity(vec32, vec33);
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }

                if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = null;
                    if (hitResult instanceof EntityHitResult) {
                        entity = ((EntityHitResult) hitResult).getEntity();
                    }

                    if (entity instanceof Player player1 && this.getOwner() instanceof Player player2 && !player2.canHarmPlayer(player1)) {
                        hitResult = null;
                        entityHitResult = null;
                    }
                }

                if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                    this.hasImpulse = true;
                    if (this.hitTargetOrDeflectSelf(hitResult) != ProjectileDeflection.NONE) {
                        break;
                    }
                }

                if (entityHitResult == null || this.getPierceLevel() <= 0) {
                    break;
                }

                hitResult = null;
            }

            vec3 = this.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;
            if (this.isCritArrow()) {
                for (int i = 0; i < 4; i++) {
                    this.level().addParticle(ParticleTypes.CRIT,
                            this.getX() + d5 * (double)i / 4.0,
                            this.getY() + d6 * (double)i / 4.0,
                            this.getZ() + d1 * (double)i / 4.0,
                            -d5, -d6 + 0.2, -d1);
                }
            }

            double d7 = this.getX() + d5;
            double d2 = this.getY() + d6;
            double d3 = this.getZ() + d1;
            double d4 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(d5, d1) * 180.0F / Mth.PI));
            this.setXRot((float)(Mth.atan2(d6, d4) * 180.0F / Mth.PI));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            if (this.isInWater()) {
                for (int j = 0; j < 4; j++) {
                    this.level().addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25, d2 - d6 * 0.25, d3 - d1 * 0.25, d5, d6, d1);
                }
            }

            this.setPos(d7, d2, d3);
            this.checkInsideBlocks();
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        if (!this.level().isClientSide()) {
            Vec3 vec3 = this.position();
            this.level().explode(this, (null), EXPLOSION_DAMAGE_CALCULATOR,
                    vec3.x(), vec3.y(), vec3.z(), 1.5F, Boolean.FALSE,
                    Level.ExplosionInteraction.TRIGGER,
                    ParticleTypes.GUST_EMITTER_SMALL,
                    ParticleTypes.GUST_EMITTER_LARGE,
                    SoundEvents.WIND_CHARGE_BURST);
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(NTItems.BREEZE_ARROW);
    }

    @Override
    protected float getWaterInertia() {
        return 1.0F;
    }

}