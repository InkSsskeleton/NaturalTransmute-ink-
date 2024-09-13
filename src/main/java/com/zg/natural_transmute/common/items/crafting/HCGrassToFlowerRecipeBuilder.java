package com.zg.natural_transmute.common.items.crafting;

import com.zg.natural_transmute.common.data.recipes.HarmoniousChangeRecipeBuilder;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HCGrassToFlowerRecipeBuilder extends HarmoniousChangeRecipeBuilder {

    public HCGrassToFlowerRecipeBuilder(HarmoniousChangeSerializer.Factory<?> factory) {
        super(Ingredient.of(Items.SHORT_GRASS, Items.TALL_GRASS, Items.FERN), Ingredient.of(Items.AIR), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MEADOW.get()), Ingredient.of(ItemTags.FLOWERS), 1, 160, Boolean.TRUE, factory);
    }

    @Override
    public Item getResult() {
        HashMap<ItemLike, Float> map = new HashMap<>();
        TreeMap<Float, ItemLike> treeMap = new TreeMap<>();
        map.put(Items.CORNFLOWER, 0.2F);
        map.put(Items.AZURE_BLUET, 0.2F);
        map.put(Items.DANDELION, 0.1F);
        map.put(Items.POPPY, 0.1F);
        map.put(Items.ALLIUM, 0.2F);
        map.put(Items.OXEYE_DAISY, 0.2F);
        float sum = 0.0F;
        for (Map.Entry<ItemLike, Float> entry : map.entrySet()) {
            sum += entry.getValue();
            treeMap.put(sum, entry.getKey());
        }

        float ceilingKey = treeMap.ceilingKey(RandomSource.create().nextFloat());
        return treeMap.get(ceilingKey).asItem();
    }

}