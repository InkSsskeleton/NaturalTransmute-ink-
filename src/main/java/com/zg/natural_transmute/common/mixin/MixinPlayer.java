package com.zg.natural_transmute.common.mixin;

import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity {

    protected MixinPlayer(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "hurtArmor", at = @At(value = "HEAD"))
    protected void hurtArmor(DamageSource damageSource, float damage, CallbackInfo ci) {
        double totalProbability = 1.0D;
        for (ItemStack stack : this.getArmorSlots()) {
            if (stack.has(NTDataComponents.CAN_SPAWN_DRAGON_BREATHE)) {
                totalProbability *= 0.75F;
            }
        }

        if (!this.level().isClientSide && Math.random() < 1.0F - totalProbability) {
            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            areaEffectCloud.setParticle(ParticleTypes.DRAGON_BREATH);
            areaEffectCloud.setRadius(3.0F);
            areaEffectCloud.setDuration(600);
            areaEffectCloud.setRadiusPerTick((7.0F - areaEffectCloud.getRadius()) / areaEffectCloud.getDuration());
            areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));
            this.level().levelEvent(2006, this.blockPosition(), this.isSilent() ? -1 : 1);
            this.level().addFreshEntity(areaEffectCloud);
        }
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