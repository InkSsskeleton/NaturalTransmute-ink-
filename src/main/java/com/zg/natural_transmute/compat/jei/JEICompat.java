package com.zg.natural_transmute.compat.jei;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.client.gui.HarmoniousChangeStoveScreen;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.compat.RecipeViewerConstants;
import com.zg.natural_transmute.compat.jei.categories.HarmoniousChangeCategory;
import com.zg.natural_transmute.registry.NTBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEICompat implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return NaturalTransmute.prefix("jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<RecipeHolder<HarmoniousChangeRecipe>> hcRecipes = RecipeViewerConstants.getAllHarmoniousChangeRecipes(manager);
        registration.addRecipes(HarmoniousChangeCategory.HARMONIOUS_CHANGE_STOVE_RECIPE, hcRecipes.stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new HarmoniousChangeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(NTBlocks.HARMONIOUS_CHANGE_STOVE.get()),
                HarmoniousChangeCategory.HARMONIOUS_CHANGE_STOVE_RECIPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(HarmoniousChangeStoveScreen.class, 63, 36, 20, 20,
                HarmoniousChangeCategory.HARMONIOUS_CHANGE_STOVE_RECIPE);
    }

}