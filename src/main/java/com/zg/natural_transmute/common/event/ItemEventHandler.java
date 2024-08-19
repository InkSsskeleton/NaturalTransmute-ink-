package com.zg.natural_transmute.common.event;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.components.AssociatedBiomes;
import com.zg.natural_transmute.registry.NTDataComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@EventBusSubscriber(modid = NaturalTransmute.MOD_ID)
public class ItemEventHandler {

    @SubscribeEvent
    public static void onItemTooltips(ItemTooltipEvent event) {
        AssociatedBiomes biomes = event.getItemStack().get(NTDataComponents.ASSOCIATED_BIOMES);
        if (biomes != null) {
            List<Component> components = event.getToolTip();
            biomes.addToTooltip(event.getContext(), components::add, event.getFlags());
        }
    }

}