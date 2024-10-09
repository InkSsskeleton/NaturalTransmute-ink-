package com.zg.natural_transmute.common.event.handler;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = NaturalTransmute.MOD_ID)
public class ItemEventHandler {

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addStartMix(NTItems.SILVERFISH_PUPA.get(), Potions.INFESTED);
        BuiltInRegistries.POTION.stream().filter(potion -> potion != Potions.WATER && potion != Potions.AWKWARD)
                .forEach(potion -> builder.addMix(Holder.direct(potion), NTItems.WARPED_WART.get(), Potions.AWKWARD));
    }

}