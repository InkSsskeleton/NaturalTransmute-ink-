package com.zg.natural_transmute.common.data.recipes;

import com.zg.natural_transmute.common.items.crafting.GatheringPlatformRecipe;
import com.zg.natural_transmute.common.items.crafting.GatheringPlatformSerializer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class GatheringPlatformRecipeBuilder implements RecipeBuilder {

    protected final Ingredient input1;
    protected final Ingredient input2;
    protected final ItemStack core;
    protected final ItemStack result;
    protected final int gatheringTime;
    protected final GatheringPlatformSerializer.Factory<?> factory;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public GatheringPlatformRecipeBuilder(
            Ingredient input1, Ingredient input2,
            ItemStack core, ItemStack result, int gatheringTime,
            GatheringPlatformSerializer.Factory<?> factory) {
        this.input1 = input1;
        this.input2 = input2;
        this.core = core;
        this.result = result;
        this.gatheringTime = gatheringTime;
        this.factory = factory;
    }

    public static GatheringPlatformRecipeBuilder addRecipe(
            Ingredient input1, Ingredient input2, Item core, Item result, int gatheringTime) {
        return new GatheringPlatformRecipeBuilder(input1, input2,
                core.getDefaultInstance(), result.getDefaultInstance(),
                gatheringTime, GatheringPlatformRecipe::new);
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
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        GatheringPlatformRecipe recipe = this.factory.create(
                this.input1, this.input2, this.core, this.result, this.gatheringTime);
        ResourceLocation r1 = id.withPrefix("gathering_platform/");
        ResourceLocation r2 = id.withPrefix("recipes/gathering_platform/");
        recipeOutput.accept(r1, recipe, builder.build(r2));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

}