package com.zg.natural_transmute.common.items.crafting;

import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HarmoniousChangeRecipe implements Recipe<HarmoniousChangeRecipeInput> {

    public final Ingredient input1;
    public final Ingredient input2;
    public final Ingredient input3;
    public final Ingredient fuXiang;
    public final Ingredient results;
    public final int amount;
    public final int time;
    public final boolean shouldConsume;

    public HarmoniousChangeRecipe(
            Ingredient input1, Ingredient input2, Ingredient input3,
            Ingredient fuXiang, Ingredient results,
            int amount, int time, boolean shouldConsume) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.fuXiang = fuXiang;
        this.results = results;
        this.amount = amount;
        this.time = time;
        this.shouldConsume = shouldConsume;
    }

    @Override
    public boolean matches(HarmoniousChangeRecipeInput input, Level level) {
        boolean flagA = false, b1 = false, b2 = false, b3 = false;
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                items.add(item);
            }

            if (this.input1.test(item)) {
                b1 = true;
            } else if (this.input2.test(item)) {
                b2 = true;
            } else if (this.input3.test(item)) {
                b3 = true;
            }
        }

        if (items.size() == 1) {
            ItemStack item = items.getFirst();
            if (this.input1.test(item) || this.input2.test(item) || this.input3.test(item)) {
                flagA = true;
            }
        }

        boolean flagB = b1 && b2 && b3;
        boolean hasFuel = HarmoniousChangeStoveBlockEntity.getFuel()
                .containsKey(input.getItem(3).getItem());
        return this.fuXiang.test(input.getItem(4)) && hasFuel && (flagA || flagB);
    }

    @Override
    public ItemStack assemble(HarmoniousChangeRecipeInput input, HolderLookup.Provider registries) {
        return this.results.getItems()[0].copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public Ingredient getInput2() {
        return this.input2;
    }

    public NonNullList<ItemStack> getResultItemList() {
        NonNullList<ItemStack> nonNullList = NonNullList.create();
        nonNullList.addAll(Arrays.stream(this.results.getItems()).toList());
        return nonNullList;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.getResultItemList().getFirst();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(NTBlocks.HARMONIOUS_CHANGE_STOVE.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipes.HARMONIOUS_CHANGE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return NTRecipes.HARMONIOUS_CHANGE_RECIPE.get();
    }

}