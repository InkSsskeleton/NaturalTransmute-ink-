package com.zg.natural_transmute.common.entities.projectile;

import com.zg.natural_transmute.registry.NTEntityTypes;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.utils.NTEntityUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SimpleExplosionDamageCalculator;
import net.minecraft.world.phys.Vec3;
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
        NTEntityUtils.arrowTickNoWaterInertia(this);
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