package com.zg.natural_transmute.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NTEntityUtils {

    public static void arrowTickNoWaterInertia(AbstractArrow arrow) {
        if (!arrow.hasBeenShot) {
            arrow.gameEvent(GameEvent.PROJECTILE_SHOOT, arrow.getOwner());
            arrow.hasBeenShot = true;
        }

        if (!arrow.leftOwner) {
            arrow.leftOwner = arrow.checkLeftOwner();
        }

        arrow.baseTick();
        Vec3 vec3 = arrow.getDeltaMovement();
        if (arrow.xRotO == 0.0F && arrow.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            arrow.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / Mth.PI));
            arrow.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / Mth.PI));
            arrow.yRotO = arrow.getYRot();
            arrow.xRotO = arrow.getXRot();
        }

        BlockPos blockPos = arrow.blockPosition();
        BlockState blockState = arrow.level().getBlockState(blockPos);
        if (!blockState.isAir()) {
            VoxelShape voxelShape = blockState.getCollisionShape(arrow.level(), blockPos);
            if (!voxelShape.isEmpty()) {
                Vec3 vec31 = arrow.position();
                for (AABB aabb : voxelShape.toAabbs()) {
                    if (aabb.move(blockPos).contains(vec31)) {
                        arrow.inGround = true;
                        break;
                    }
                }
            }
        }

        if (arrow.shakeTime > 0) {
            arrow.shakeTime--;
        }

        if (arrow.isInWaterOrRain() || blockState.is(Blocks.POWDER_SNOW) || arrow.isInFluidType((fluidType, height) -> arrow.canFluidExtinguish(fluidType))) {
            arrow.clearFire();
        }

        if (arrow.inGround) {
            if (arrow.lastState != blockState && arrow.shouldFall()) {
                arrow.startFalling();
            } else if (!arrow.level().isClientSide) {
                arrow.tickDespawn();
            }

            arrow.inGroundTime++;
        } else {
            arrow.inGroundTime = 0;
            Vec3 vec32 = arrow.position();
            Vec3 vec33 = vec32.add(vec3);
            HitResult hitResult = arrow.level().clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, arrow));
            if (hitResult.getType() != HitResult.Type.MISS) {
                vec33 = hitResult.getLocation();
            }

            while (!arrow.isRemoved()) {
                EntityHitResult entityHitResult = arrow.findHitEntity(vec32, vec33);
                if (entityHitResult != null) {
                    hitResult = entityHitResult;
                }

                if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = null;
                    if (hitResult instanceof EntityHitResult) {
                        entity = ((EntityHitResult) hitResult).getEntity();
                    }

                    if (entity instanceof Player player1 && arrow.getOwner() instanceof Player player2 && !player2.canHarmPlayer(player1)) {
                        hitResult = null;
                        entityHitResult = null;
                    }
                }

                if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                    arrow.hasImpulse = true;
                    if (arrow.hitTargetOrDeflectSelf(hitResult) != ProjectileDeflection.NONE) {
                        break;
                    }
                }

                if (entityHitResult == null || arrow.getPierceLevel() <= 0) {
                    break;
                }

                hitResult = null;
            }

            vec3 = arrow.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;
            if (arrow.isCritArrow()) {
                for (int i = 0; i < 4; i++) {
                    arrow.level().addParticle(ParticleTypes.CRIT,
                            arrow.getX() + d5 * (double)i / 4.0,
                            arrow.getY() + d6 * (double)i / 4.0,
                            arrow.getZ() + d1 * (double)i / 4.0,
                            -d5, -d6 + 0.2, -d1);
                }
            }

            double d7 = arrow.getX() + d5;
            double d2 = arrow.getY() + d6;
            double d3 = arrow.getZ() + d1;
            double d4 = vec3.horizontalDistance();
            arrow.setYRot((float)(Mth.atan2(d5, d1) * 180.0F / Mth.PI));
            arrow.setXRot((float)(Mth.atan2(d6, d4) * 180.0F / Mth.PI));
            arrow.setXRot(Projectile.lerpRotation(arrow.xRotO, arrow.getXRot()));
            arrow.setYRot(Projectile.lerpRotation(arrow.yRotO, arrow.getYRot()));
            if (arrow.isInWater()) {
                for (int j = 0; j < 4; j++) {
                    arrow.level().addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25, d2 - d6 * 0.25, d3 - d1 * 0.25, d5, d6, d1);
                }
            }

            arrow.setPos(d7, d2, d3);
            arrow.checkInsideBlocks();
        }
    }
    
}