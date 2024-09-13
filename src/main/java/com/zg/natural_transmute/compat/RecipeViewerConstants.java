package com.zg.natural_transmute.compat;

import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.registry.NTRecipes;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

public class RecipeViewerConstants {

    public static List<RecipeHolder<HarmoniousChangeRecipe>> getAllHarmoniousChangeRecipes(RecipeManager manager) {
        return new ArrayList<>(manager.getAllRecipesFor(NTRecipes.HARMONIOUS_CHANGE_RECIPE.get()));
    }

}