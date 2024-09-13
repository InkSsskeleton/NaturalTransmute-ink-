package com.zg.natural_transmute.common.event;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = NaturalTransmute.MOD_ID)
public class MiscEventHandler {

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        HarmoniousChangeStoveBlockEntity.invalidateCache();
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        for (ItemStack stack : event.getEntity().getArmorSlots()) {
            boolean b1 = event.getSource().is(DamageTypes.MAGIC);
            boolean b2 = event.getSource().is(DamageTypes.INDIRECT_MAGIC);
            if (stack.has(NTDataComponents.CAN_SPAWN_DRAGON_BREATHE) && (b1 || b2)) {
                event.setNewDamage(0.0F); break;
            }
        }
    }

}