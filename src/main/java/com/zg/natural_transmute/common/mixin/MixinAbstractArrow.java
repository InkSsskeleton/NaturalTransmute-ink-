package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(AbstractArrow.class)
public class MixinAbstractArrow {

    @Shadow @Nullable
    public ItemStack firedFromWeapon;

    @Inject(method = "getWaterInertia", at = @At(value = "HEAD"), cancellable = true)
    protected void getWaterInertia(CallbackInfoReturnable<Float> cir) {
        if (this.firedFromWeapon != null && this.firedFromWeapon.is(NTItems.WHALE_BONE_BOW)) {
            cir.setReturnValue(1.0F);
        }
    }

}