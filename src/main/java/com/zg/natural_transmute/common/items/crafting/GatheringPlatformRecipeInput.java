package com.zg.natural_transmute.common.items.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record GatheringPlatformRecipeInput(ItemStack input1, ItemStack input2, ItemStack core) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> this.input1;
            case 1 -> this.input2;
            case 2 -> this.core;
            default -> throw new IllegalArgumentException("No item for index " + index);
        };
    }

    @Override
    public int size() {
        return 3;
    }

}