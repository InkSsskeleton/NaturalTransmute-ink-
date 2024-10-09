package com.zg.natural_transmute.common.items.crafting;

import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.*;

public class HCGrassToFlowerRecipe extends HarmoniousChangeRecipe {

    public HCGrassToFlowerRecipe() {
        super(Ingredient.of(Items.SHORT_GRASS, Items.TALL_GRASS, Items.FERN), Ingredient.of(Items.AIR), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MEADOW.get()), Ingredient.of(ItemTags.FLOWERS), 1, 160, Boolean.TRUE);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        HashMap<ItemLike, Integer> map = new HashMap<>();
        RandomSource random = RandomSource.create();
        map.put(Items.CORNFLOWER, 2);
        map.put(Items.AZURE_BLUET, 2);
        map.put(Items.DANDELION, 1);
        map.put(Items.POPPY, 1);
        map.put(Items.ALLIUM, 2);
        map.put(Items.OXEYE_DAISY, 2);
        int totalWeight = map.values().stream().mapToInt(i -> i).sum();
        int randomWeight = random.nextInt(totalWeight) + 1;
        int currentWeight = 0;
        for (ItemLike itemLike : map.keySet()) {
            currentWeight += map.get(itemLike);
            if (currentWeight > randomWeight) {
                return new ItemStack(itemLike);
            }
        }

        return super.getResultItem(registries);
    }

}