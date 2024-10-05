package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    protected MixinPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getProjectile", at = @At(value = "HEAD"), cancellable = true)
    public void getProjectile(ItemStack shootable, CallbackInfoReturnable<ItemStack> cir) {
        if (shootable.getItem() instanceof CrossbowItem) {
            Item item = NTItems.REFRIGERATED_ROCKET.get();
            ItemStack itemInOffHand = this.getItemInHand(InteractionHand.OFF_HAND);
            ItemStack itemInMainHand = this.getItemInHand(InteractionHand.MAIN_HAND);
            if (itemInOffHand.is(item)) {
                cir.setReturnValue(itemInOffHand);
            } else {
                cir.setReturnValue(itemInMainHand.is(item) ? itemInMainHand : ItemStack.EMPTY);
            }
        }
    }

}