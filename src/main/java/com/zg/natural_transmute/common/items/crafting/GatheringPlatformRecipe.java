package com.zg.natural_transmute.common.items.crafting;

import com.zg.natural_transmute.common.blocks.entity.GatheringPlatformBlockEntity;
import com.zg.natural_transmute.registry.NTRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class GatheringPlatformRecipe implements Recipe<GatheringPlatformRecipeInput> {

    protected final Ingredient input1;
    protected final Ingredient input2;
    protected final ItemStack core;
    protected final ItemStack result;
    public int gatheringTime;

    public GatheringPlatformRecipe(
            Ingredient input1, Ingredient input2,
            ItemStack core, ItemStack result, int gatheringTime) {
        this.input1 = input1;
        this.input2 = input2;
        this.core = core;
        this.result = result;
        this.gatheringTime = gatheringTime;
    }

    @Override
    public boolean matches(GatheringPlatformRecipeInput input, Level level) {
        ItemStack item1 = input.getItem(0);
        ItemStack item2 = input.getItem(2);
        boolean flag1 = this.input1.test(item1) && this.input2.test(item2);
        boolean flag2 = this.input1.test(item2) && this.input2.test(item1);
        return (flag1 || flag2) && this.core.is(input.getItem(1).getItem());
    }

    @Override
    public ItemStack assemble(GatheringPlatformRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return NTRecipes.GATHERING_PLATFORM_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return NTRecipes.GATHERING_PLATFORM_RECIPE.get();
    }

    public void consumeIngredients(GatheringPlatformBlockEntity table) {
        for (int i = 0; i < 3; i++) {
            ItemStack item = table.getItem(i);
            if (item.hasCraftingRemainingItem()) {
                ItemStack remainingItem = item.getCraftingRemainingItem();
                if (item.getCount() == 1) {
                    table.setItem(i, remainingItem);
                } else {
                    Level level = table.getLevel();
                    if (level != null) {
                        float x = table.getBlockPos().getX() + 0.5F;
                        float y = table.getBlockPos().getY() + 1.2F;
                        float z = table.getBlockPos().getZ() + 0.5F;
                        Containers.dropItemStack(level, x, y, z, remainingItem);
                        item.shrink(1);
                    }
                }
            } else {
                item.shrink(1);
            }
        }
    }

}