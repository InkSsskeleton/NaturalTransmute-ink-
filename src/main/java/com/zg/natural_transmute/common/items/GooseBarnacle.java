package com.zg.natural_transmute.common.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GooseBarnacle extends Item {

    public GooseBarnacle(Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.level() instanceof ServerLevel && !attacker.hasInfiniteMaterials()) {
            if (attacker.getRandom().nextBoolean()) {
                attacker.onEquippedItemBroken(this, EquipmentSlot.MAINHAND);
                stack.consume(1, attacker);
            }
        }
    }

}