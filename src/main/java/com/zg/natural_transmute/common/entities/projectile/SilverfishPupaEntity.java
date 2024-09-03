package com.zg.natural_transmute.common.entities.projectile;

import com.zg.natural_transmute.registry.NTEntityTypes;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class SilverfishPupaEntity extends ThrowableItemProjectile {

    public SilverfishPupaEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public SilverfishPupaEntity(Level level, double x, double y, double z) {
        super(NTEntityTypes.SILVERFISH_PUPA.get(), x, y, z, level);
    }

    public SilverfishPupaEntity(Level level, LivingEntity shooter) {
        super(NTEntityTypes.SILVERFISH_PUPA.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return NTItems.SILVERFISH_PUPA.get();
    }

    private ParticleOptions getParticle() {
        return new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particleOptions = this.getParticle();
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Level level = this.level();
        if (!level.isClientSide) {
            level.broadcastEntityEvent(this, (byte)3);
            Silverfish silverfish = new Silverfish(EntityType.SILVERFISH, level);
            silverfish.setPos(result.getLocation());
            level.addFreshEntity(silverfish);
            this.playSound(SoundEvents.SILVERFISH_HURT);
            this.discard();
        }
    }

}