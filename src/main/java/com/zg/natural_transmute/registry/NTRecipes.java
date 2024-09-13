package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.items.crafting.GatheringRecipe;
import com.zg.natural_transmute.common.items.crafting.GatheringSerializer;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeRecipe;
import com.zg.natural_transmute.common.items.crafting.HarmoniousChangeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, NaturalTransmute.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, NaturalTransmute.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<GatheringRecipe>> GATHERING_RECIPE =
            RECIPE_TYPES.register("gathering", () -> RecipeType.simple(NaturalTransmute.prefix("gathering")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<HarmoniousChangeRecipe>> HARMONIOUS_CHANGE_RECIPE =
            RECIPE_TYPES.register("harmonious_change", () -> RecipeType.simple(NaturalTransmute.prefix("harmonious_change")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GatheringRecipe>> GATHERING_SERIALIZER =
            RECIPE_SERIALIZERS.register("gathering", () -> new GatheringSerializer<>(GatheringRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HarmoniousChangeRecipe>> HARMONIOUS_CHANGE_SERIALIZER =
            RECIPE_SERIALIZERS.register("harmonious_change", () -> new HarmoniousChangeSerializer<>(HarmoniousChangeRecipe::new));

}