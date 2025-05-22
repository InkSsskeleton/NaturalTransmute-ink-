package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.utils.NTEntityUtils;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.extensions.IEntityExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

@Mixin(AbstractArrow.class)
public abstract class MixinAbstractArrow implements IEntityExtension {

    @Shadow @Nullable
    public ItemStack firedFromWeapon;

    @Inject(method = "tick", at = @At(value = "HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        if (this.firedFromWeapon != null && this.firedFromWeapon.is(NTItems.WHALE_BONE_BOW)) {
            try {
                Class<IEntityExtension> clazz = IEntityExtension.class;
                Method method = clazz.getDeclaredMethod("self");
                method.setAccessible(true);
                Object object = method.invoke(this);
                if (object instanceof AbstractArrow arrow) {
                    NTEntityUtils.arrowTickNoWaterInertia(arrow);
                    ci.cancel();
                }

            } catch (Exception ignored) {}
        }
    }

}