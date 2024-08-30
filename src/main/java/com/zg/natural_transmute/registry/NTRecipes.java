package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.items.crafting.GatheringPlatformRecipe;
import com.zg.natural_transmute.common.items.crafting.GatheringPlatformSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, NaturalTransmute.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, NaturalTransmute.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<GatheringPlatformRecipe>> GATHERING_PLATFORM_RECIPE =
            RECIPE_TYPES.register("gathering_platform", () -> RecipeType.simple(NaturalTransmute.prefix("gathering_platform")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GatheringPlatformRecipe>> GATHERING_PLATFORM_SERIALIZER =
            RECIPE_SERIALIZERS.register("gathering_platform", () -> new GatheringPlatformSerializer<>(GatheringPlatformRecipe::new));

}