package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(AbstractArrow.class)
public abstract class MixinAbstractArrow extends Projectile {

    @Shadow @Nullable private ItemStack firedFromWeapon;
    @Shadow protected abstract float getWaterInertia();

    protected MixinAbstractArrow(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;getWaterInertia()F"))
    public float tick(AbstractArrow arrow) {
        if (this.firedFromWeapon != null && this.firedFromWeapon.is(NTItems.WHALE_BONE_BOW)) {
            return 1.0F;
        }

        return this.getWaterInertia();
    }

}