package com.zg.natural_transmute.common.data.provider;

import com.google.common.collect.Sets;
import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.data.recipes.GatheringPlatformRecipeBuilder;
import com.zg.natural_transmute.common.items.WaterWax;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.WithConditions;
import net.neoforged.neoforge.common.crafting.BlockTagIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class NTRecipeProvider extends RecipeProvider {

    public NTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        waterWaxRecipes(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 6)
                .requires(NTItems.WHALE_BONE.get()).group("bonemeal")
                .unlockedBy("has_whale_bone", has(NTItems.WHALE_BONE.get()))
                .save(recipeOutput, NaturalTransmute.prefix("whale_bone_to_bonemeal"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.HARMONIOUS_CHANGE_FUEL.get())
                .requires(Ingredient.of(Items.AMETHYST_SHARD, Items.LAPIS_LAZULI)).requires(ItemTags.COALS)
                .unlockedBy("has_coals", has(ItemTags.COALS)).save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, NTItems.CORUNDUM_IRON_PLATE.get())
                .requires(Ingredient.of(NTItems.CORUNDUM.get(), Items.IRON_INGOT))
                .unlockedBy("has_corundum", has(NTItems.CORUNDUM.get())).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.PAPER, 3)
                .define('#', Blocks.SUGAR_CANE).pattern("###")
                .unlockedBy("has_papyrus", has(NTItems.PAPYRUS.get()))
                .save(recipeOutput, NaturalTransmute.prefix("papyrus_to_paper"));
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.JUNGLE_PLANKS)
                .define('#', NTItems.COCONUT_SHELL.get())
                .pattern("##").pattern("##")
                .unlockedBy("has_coconut_shell", has(NTItems.COCONUT_SHELL.get()))
                .save(recipeOutput, NaturalTransmute.prefix("coconut_shell_to_jungle_plank"));
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, NTItems.WHALE_BONE_BOW.get())
                .define('#', NTItems.WHALE_BONE.get()).define('X', Items.STRING)
                .pattern(" #X").pattern("# X").pattern(" #X")
                .unlockedBy("has_string", has(Items.STRING)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.HARMONIOUS_CHANGE_CORE.get())
                .define('H', NTItems.HETEROGENEOUS_STONE.get()).define('I', Items.DIAMOND)
                .pattern(" H ").pattern("HIH").pattern(" H ")
                .unlockedBy(getHasName(NTItems.HETEROGENEOUS_STONE.get()),
                        has(NTItems.HETEROGENEOUS_STONE.get())).save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NTItems.DUCK.get()), RecipeCategory.FOOD,
                        NTItems.COOKED_DUCK.get(), 0.35F, 200)
                .unlockedBy("has_chicken", has(NTItems.DUCK.get())).save(recipeOutput);
        gathering(recipeOutput, Items.SAND, Items.SAND, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_DESERT.get(), 160);
        gathering(recipeOutput, Items.RED_SAND, Items.RED_SAND, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_BADLANDS.get(), 160);
        gathering(recipeOutput, Items.OAK_SAPLING, Items.OAK_SAPLING, NTItems.H_BADLANDS.get(), NTItems.H_WOODED_BADLANDS.get(), 320);
        gathering(recipeOutput, Items.SNOW_BLOCK, Items.SNOW_BLOCK, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_SNOWY_TUNDRA.get(), 160);
        gathering(recipeOutput, Items.PACKED_ICE, Items.PACKED_ICE, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_ICE_SPIKES.get(), 160);
        gathering(recipeOutput, Ingredient.of(Items.GRAVEL, Items.STONE, Items.ANDESITE, Items.GRANITE, Items.DIORITE),
                Ingredient.of(Items.EMERALD), NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_MOUNTAINS.get(), 160);
        gathering(recipeOutput, Items.SEAGRASS, Items.GRAVEL, NTItems.H_OCEAN.get(), NTItems.H_STONE_SHORE.get(), 320);
        gathering(recipeOutput, Items.BLUE_ICE, Items.POWDER_SNOW_BUCKET, NTItems.H_MOUNTAINS.get(), NTItems.H_SNOWY_SLOPES.get(), 320);
        gathering(recipeOutput, Ingredient.of(Items.GRASS_BLOCK), Ingredient.of(ItemTags.FLOWERS),
                NTItems.H_MOUNTAINS.get(), NTItems.H_MEADOW.get(), 320);
        gathering(recipeOutput, Ingredient.of(Items.OAK_SAPLING, Items.OAK_LEAVES, Items.OAK_LOG),
                Ingredient.of(Items.OAK_SAPLING, Items.OAK_LEAVES, Items.OAK_LOG),
                NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_FOREST.get(), 160);
        gathering(recipeOutput, Ingredient.of(ItemTags.FLOWERS), Ingredient.of(ItemTags.FLOWERS),
                NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get(), 320);
        gathering(recipeOutput, Ingredient.of(Items.DARK_OAK_SAPLING, Items.DARK_OAK_LEAVES, Items.DARK_OAK_LOG),
                Ingredient.of(Items.DARK_OAK_SAPLING, Items.DARK_OAK_LEAVES, Items.DARK_OAK_LOG),
                NTItems.H_FOREST.get(), NTItems.H_DARK_FOREST.get(), 320);
        gathering(recipeOutput, Ingredient.of(Items.BIRCH_SAPLING, Items.BIRCH_LEAVES, Items.BIRCH_LOG),
                Ingredient.of(Items.BIRCH_SAPLING, Items.BIRCH_LEAVES, Items.BIRCH_LOG),
                NTItems.H_FOREST.get(), NTItems.H_BIRCH_FOREST.get(), 320);
        gathering(recipeOutput, Items.MYCELIUM, Items.MYCELIUM, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_MUSHROOM.get(), 160);
        gathering(recipeOutput, Items.SLIME_BALL, Items.SLIME_BALL, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_SWAMP.get(), 160);
        gathering(recipeOutput, Ingredient.of(Items.MANGROVE_PROPAGULE, Items.MANGROVE_LEAVES, Items.MANGROVE_LOG),
                Ingredient.of(Items.MANGROVE_PROPAGULE, Items.MANGROVE_LEAVES, Items.MANGROVE_LOG),
                NTItems.H_SWAMP.get(), NTItems.H_MANGROVE_SWAMP.get(), 320);
        gathering(recipeOutput, Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS), Ingredient.of(Items.DIRT),
                NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_PLAINS.get(), 160);
        gathering(recipeOutput, Ingredient.of(Items.JUNGLE_SAPLING, Items.JUNGLE_LEAVES, Items.JUNGLE_LOG),
                Ingredient.of(Items.JUNGLE_SAPLING, Items.JUNGLE_LEAVES, Items.JUNGLE_LOG),
                NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_JUNGLE.get(), 160);
        gathering(recipeOutput, Ingredient.of(Items.ACACIA_SAPLING, Items.ACACIA_LEAVES, Items.ACACIA_LOG),
                Ingredient.of(Items.ACACIA_SAPLING, Items.ACACIA_LEAVES, Items.ACACIA_LOG),
                NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_SAVANNA.get(), 160);
        gathering(recipeOutput, Items.CLAY, Items.WATER_BUCKET, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_RIVER.get(), 160);
        gathering(recipeOutput, Items.KELP, Items.WATER_BUCKET, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_OCEAN.get(), 160);
        gathering(recipeOutput, new BlockTagIngredient(BlockTags.ICE).toVanilla(),
                new BlockTagIngredient(BlockTags.ICE).toVanilla(),
                NTItems.H_OCEAN.get(), NTItems.H_FROZEN_OCEAN.get(), 320);
        gathering(recipeOutput, Items.KELP, Items.TURTLE_SCUTE, NTItems.H_OCEAN.get(), NTItems.H_BEACH.get(), 320);
        gathering(recipeOutput, new BlockTagIngredient(BlockTags.CORAL_PLANTS).toVanilla(),
                new BlockTagIngredient(BlockTags.CORALS).toVanilla(),
                NTItems.H_OCEAN.get(), NTItems.H_WARM_OCEAN.get(), 320);
        gathering(recipeOutput, Ingredient.of(Items.CHERRY_SAPLING, Items.CHERRY_LEAVES, Items.CHERRY_LOG),
                Ingredient.of(Items.CHERRY_SAPLING, Items.CHERRY_LEAVES, Items.CHERRY_LOG),
                NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_CHERRY_GROVE.get(), 160);
        gathering(recipeOutput, Items.DEEPSLATE, Items.DEEPSLATE, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_DEEPSLATE.get(), 160);
        gathering(recipeOutput, Items.MOSS_BLOCK, Items.MOSS_BLOCK, NTItems.H_DEEPSLATE.get(), NTItems.H_LUSH_CAVE.get(), 320);
        gathering(recipeOutput, Items.DRIPSTONE_BLOCK, Items.DRIPSTONE_BLOCK, NTItems.H_DEEPSLATE.get(), NTItems.H_DRIPSTONE_CAVES.get(), 320);
        gathering(recipeOutput, Items.SCULK, Items.SCULK, NTItems.H_DEEPSLATE.get(), NTItems.H_DEEP_DARK.get(), 320);
        gathering(recipeOutput, Ingredient.of(Items.SPRUCE_SAPLING, Items.SPRUCE_LEAVES, Items.SPRUCE_LOG),
                Ingredient.of(Items.SPRUCE_SAPLING, Items.SPRUCE_LEAVES, Items.SPRUCE_LOG),
                NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_TAIGA.get(), 160);
        gathering(recipeOutput, Items.PODZOL, Items.MOSSY_COBBLESTONE, NTItems.H_TAIGA.get(), NTItems.H_GIANT_TREE_TAIGA.get(), 160);
        gathering(recipeOutput, Items.WIND_CHARGE, Items.WIND_CHARGE, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_WIND.get(), 160);
        gathering(recipeOutput, Items.NETHERRACK, Items.NETHERRACK, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_NETHER.get(), 160);
        gathering(recipeOutput, Items.CRIMSON_FUNGUS, Items.CRIMSON_ROOTS, NTItems.H_NETHER.get(), NTItems.H_CRIMSON_FOREST.get(), 320);
        gathering(recipeOutput, Items.WARPED_FUNGUS, Items.WARPED_ROOTS, NTItems.H_NETHER.get(), NTItems.H_WARPED_FOREST.get(), 320);
        gathering(recipeOutput, Items.SOUL_SAND, Items.BONE, NTItems.H_NETHER.get(), NTItems.H_SOUL_SAND_VALLEY.get(), 320);
        gathering(recipeOutput, Items.BASALT, Items.MAGMA_CREAM, NTItems.H_NETHER.get(), NTItems.H_BASALT_DELTAS.get(), 320);
        gathering(recipeOutput, Items.END_STONE, Items.CHORUS_FRUIT, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_END_HIGHLANDS.get(), 160);
        gathering(recipeOutput, Items.DRAGON_BREATH, Items.DRAGON_BREATH, NTItems.HETEROGENEOUS_STONE.get(), NTItems.H_END.get(), 160);
        this.buildBaseToolRecipes(recipeOutput, NTItems.SCULK_BONE_SWORD.get(), NTItems.SCULK_BONE_PICKAXE.get(), NTItems.SCULK_BONE_SHOVEL.get(),
                NTItems.SCULK_BONE_AXE.get(), NTItems.SCULK_BONE_HOE.get(), Blocks.DEEPSLATE.asItem(), NTItems.SCULK_BONE.get());
    }

    @Override
    protected @NotNull CompletableFuture<?> run(CachedOutput output, HolderLookup.Provider registries) {
        final Set<ResourceLocation> set = Sets.newHashSet();
        final List<CompletableFuture<?>> list = new ArrayList<>();
        this.buildRecipes(new NTRecipeOutput(this.recipePathProvider, this.advancementPathProvider, output, registries, set, list));
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    public static void gathering(RecipeOutput recipeOutput, Ingredient input1, Ingredient input2, Item core, Item result, int gatheringTime) {
        GatheringPlatformRecipeBuilder.addRecipe(input1, input2, core, result, gatheringTime).unlockedBy(getHasName(core), has(core)).save(recipeOutput);
    }

    public static void gathering(RecipeOutput recipeOutput, ItemLike input1, ItemLike input2, Item core, Item result, int gatheringTime) {
        gathering(recipeOutput, Ingredient.of(input1), Ingredient.of(input2), core, result, gatheringTime);
    }

    private static void waterWaxRecipes(RecipeOutput recipeOutput) {
        WaterWax.CAN_USE_WATER_WAX.get().forEach((b1, b2) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, b2)
                .requires(b1).requires(NTItems.WATER_WAX.get()).group(getItemName(b2)).unlockedBy(getHasName(b1), has(b1))
                .save(recipeOutput, NaturalTransmute.prefix("water_wax/" + getConversionRecipeName(b2, NTItems.WATER_WAX.get()))));
        HoneycombItem.WAXABLES.get().forEach((b1, b2) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, b2)
                .requires(b1).requires(NTItems.WATER_WAX.get()).group(getItemName(b2)).unlockedBy(getHasName(b1), has(b1))
                .save(recipeOutput, NaturalTransmute.prefix("water_wax/" + getConversionRecipeName(b2, NTItems.WATER_WAX.get()))));
    }

    private void buildBaseToolRecipes(
            RecipeOutput recipeOutput, ItemLike sword, ItemLike pickaxe, ItemLike shovel, 
            ItemLike axe, ItemLike hoe, ItemLike material, ItemLike stick) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword)
                .define('#', stick).define('X', material)
                .pattern("X").pattern("X").pattern("#")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe)
                .define('#', stick).define('X', material)
                .pattern("XXX").pattern(" # ").pattern(" # ")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .define('#', stick).define('X', material)
                .pattern("X").pattern("#").pattern("#")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .define('#', stick).define('X', material)
                .pattern("XX").pattern("X#").pattern(" #")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hoe)
                .define('#', stick).define('X', material)
                .pattern("XX").pattern(" #").pattern(" #")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
    }

    @SuppressWarnings("removal")
    private record NTRecipeOutput(
            PackOutput.PathProvider recipePathProvider, PackOutput.PathProvider advancementPathProvider,
            CachedOutput output, HolderLookup.Provider registries, Set<ResourceLocation> set,
            List<CompletableFuture<?>> list) implements RecipeOutput {

        @Override
        public void accept(ResourceLocation id, Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
            if (this.set.add(id)) {
                this.list.add(DataProvider.saveStable(this.output, this.registries, Recipe.CONDITIONAL_CODEC,
                        Optional.of(new WithConditions<>(recipe, conditions)), this.recipePathProvider.json(id)));
                if (advancement != null) {
                    this.list.add(DataProvider.saveStable(this.output, this.registries, Advancement.CONDITIONAL_CODEC,
                            Optional.of(new WithConditions<>(advancement.value(), conditions)),
                            this.advancementPathProvider.json(advancement.id())));
                }
            }
        }

        @Override
        public Advancement.@NotNull Builder advancement() {
            return Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT);
        }

    }

}