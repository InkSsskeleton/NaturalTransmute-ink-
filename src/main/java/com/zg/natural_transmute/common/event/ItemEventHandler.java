package com.zg.natural_transmute.common.event;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = NaturalTransmute.MOD_ID)
public class ItemEventHandler {

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addStartMix(NTItems.SILVERFISH_PUPA.get(), Potions.INFESTED);
    }

}