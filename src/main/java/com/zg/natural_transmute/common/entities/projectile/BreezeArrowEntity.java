package com.zg.natural_transmute.common.entities.projectile;

import com.zg.natural_transmute.registry.NTEntityTypes;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BreezeArrowEntity extends AbstractArrow {

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
    protected void doPostHurtEffects(LivingEntity target) {
        if (!this.level().isClientSide()) {
            Vec3 vec3 = this.position();
            this.level().explode(this, null,
                    AbstractWindCharge.EXPLOSION_DAMAGE_CALCULATOR,
                    vec3.x(), vec3.y(), vec3.z(), 1.2F, Boolean.FALSE,
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