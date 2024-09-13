package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.common.entities.projectile.RefrigeratedRocketEntity;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CrossbowItem.class)
public class MixinCrossbowItem {

    @Inject(method = "createProjectile", at = @At(value = "HEAD"), cancellable = true)
    protected void createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit, CallbackInfoReturnable<Projectile> cir) {
        if (ammo.is(NTItems.REFRIGERATED_ROCKET)) {
            cir.setReturnValue(new RefrigeratedRocketEntity(level, ammo, shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), Boolean.TRUE));
        }
    }

}