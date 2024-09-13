package com.zg.natural_transmute.common.entities.projectile;

import com.zg.natural_transmute.registry.NTEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class RefrigeratedRocketEntity extends FireworkRocketEntity {

    public RefrigeratedRocketEntity(EntityType<? extends FireworkRocketEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RefrigeratedRocketEntity(Level level, @Nullable Entity shooter, double x, double y, double z, ItemStack stack) {
        super(level, shooter, x, y, z, stack);
    }

    public RefrigeratedRocketEntity(Level level, ItemStack stack, LivingEntity shooter) {
        super(level, stack, shooter);
    }

    public RefrigeratedRocketEntity(Level level, ItemStack stack, double x, double y, double z, boolean shotAtAngle) {
        super(level, stack, x, y, z, shotAtAngle);
    }

    public RefrigeratedRocketEntity(Level level, ItemStack stack, Entity shooter, double x, double y, double z, boolean shotAtAngle) {
        super(level, stack, shooter, x, y, z, shotAtAngle);
    }

    @Override
    public EntityType<?> getType() {
        return NTEntityTypes.REFRIGERATED_ROCKET.get();
    }

    @Override
    public void explode() {
        if (this.level() instanceof ServerLevel serverLevel) {
            if (this.random.nextFloat() <= 0.1F) {
                int weatherTime = ServerLevel.THUNDER_DURATION.sample(serverLevel.getRandom());
                serverLevel.setWeatherParameters((0), weatherTime, Boolean.TRUE, Boolean.TRUE);
            } else {
                int weatherTime = ServerLevel.RAIN_DURATION.sample(serverLevel.getRandom());
                serverLevel.setWeatherParameters((0), weatherTime, Boolean.TRUE, Boolean.FALSE);
            }
        }

        super.explode();
    }

}