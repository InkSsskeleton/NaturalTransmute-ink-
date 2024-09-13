package com.zg.natural_transmute.common.items.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record HarmoniousChangeRecipeInput(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack fuel, ItemStack fuXiang) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> this.input1;
            case 1 -> this.input2;
            case 2 -> this.input3;
            case 3 -> this.fuel;
            case 4 -> this.fuXiang;
            default -> throw new IllegalArgumentException("No item for index " + index);
        };
    }

    @Override
    public int size() {
        return 5;
    }

}