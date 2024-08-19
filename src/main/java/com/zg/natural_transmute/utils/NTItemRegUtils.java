package com.zg.natural_transmute.utils;

import com.zg.natural_transmute.common.components.AssociatedBiomes;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class NTItemRegUtils {

    public static DeferredHolder<Item, Item> normal(String name) {
        return NTItems.ITEMS.register(name, () -> new Item(new Item.Properties()));
    }

    public static DeferredHolder<Item, Item> fx(String name, List<ResourceKey<Biome>> biomes) {
        return NTItems.ITEMS.register(name, () -> new Item(new Item.Properties()
                .component(NTDataComponents.ASSOCIATED_BIOMES.get(), new AssociatedBiomes(biomes))));
    }

    public static DeferredHolder<Item, Item> food(String name, int nutrition, float saturation) {
        return NTItems.ITEMS.register(name, () -> new Item(new Item.Properties().food(
                new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturation).build())));
    }

}