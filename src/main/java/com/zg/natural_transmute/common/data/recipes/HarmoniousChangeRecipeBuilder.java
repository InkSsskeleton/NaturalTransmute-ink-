package com.zg.natural_transmute.common.data.recipes;

import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeSerializer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class HarmoniousChangeRecipeBuilder implements RecipeBuilder {

    public final Ingredient input1;
    public final Ingredient input2;
    public final Ingredient input3;
    public final Ingredient fuXiang;
    public final Ingredient results;
    public final int amount;
    public final int time;
    protected final boolean shouldConsume;
    protected final HarmoniousChangeSerializer.Factory<?> factory;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public HarmoniousChangeRecipeBuilder(
            Ingredient input1, Ingredient input2, Ingredient input3,
            Ingredient fuXiang, Ingredient results,
            int amount, int time, boolean shouldConsume,
            HarmoniousChangeSerializer.Factory<?> factory) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.fuXiang = fuXiang;
        this.results = results;
        this.amount = amount;
        this.time = time;
        this.shouldConsume = shouldConsume;
        this.factory = factory;
    }

    public static HarmoniousChangeRecipeBuilder addRecipe(
            Ingredient input1, Ingredient input2, Ingredient input3, Ingredient fuXiang,
            Ingredient result, int amount, int time, boolean shouldConsume) {
        return new HarmoniousChangeRecipeBuilder(
                input1, input2, input3, fuXiang, result, amount,
                time, shouldConsume, HarmoniousChangeRecipe::new);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return null;
    }

    @Override
    public Item getResult() {
        return this.results.getItems()[0].getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        HarmoniousChangeRecipe recipe = this.factory.create(
                this.input1, this.input2, this.input3, this.fuXiang,
                this.results, this.amount, this.time, this.shouldConsume);
        ResourceLocation r1 = id.withPrefix("harmonious_change/");
        ResourceLocation r2 = id.withPrefix("recipes/harmonious_change/");
        recipeOutput.accept(r1, recipe, builder.build(r2));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

}