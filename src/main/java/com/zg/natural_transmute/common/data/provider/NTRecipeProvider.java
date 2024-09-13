package com.zg.natural_transmute.common.data.provider;

import com.google.common.collect.Sets;
import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.ActivatedCoralWallFan;
import com.zg.natural_transmute.common.data.NTBlockFamilies;
import com.zg.natural_transmute.common.data.recipes.GatheringRecipeBuilder;
import com.zg.natural_transmute.common.data.recipes.HarmoniousChangeRecipeBuilder;
import com.zg.natural_transmute.common.data.tags.NTItemTags;
import com.zg.natural_transmute.common.items.WaterWax;
import com.zg.natural_transmute.common.items.crafting.HCGrassToFlowerRecipe;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.WithConditions;
import net.neoforged.neoforge.common.crafting.BlockTagIngredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
public class NTRecipeProvider extends RecipeProvider {

    private static int harmoniousChangeRecipeId = 1;
    private static final List<BlockFamily> WOOD_BLOCK_FAMILIES = List.of(
            BlockFamilies.ACACIA_PLANKS, BlockFamilies.CHERRY_PLANKS,
            BlockFamilies.BIRCH_PLANKS, BlockFamilies.CRIMSON_PLANKS,
            BlockFamilies.JUNGLE_PLANKS, BlockFamilies.OAK_PLANKS,
            BlockFamilies.DARK_OAK_PLANKS, BlockFamilies.SPRUCE_PLANKS,
            BlockFamilies.WARPED_PLANKS, BlockFamilies.MANGROVE_PLANKS);
    private static final List<Block> WOOD_BASE_BLOCKS =
            WOOD_BLOCK_FAMILIES.stream().map(BlockFamily::getBaseBlock).toList();

    public NTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        waterWaxRecipes(recipeOutput);
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, NTItems.AMBER.get(),
                RecipeCategory.BUILDING_BLOCKS, NTBlocks.AMBER_BLOCK.get());
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, NTBlocks.BLUE_NETHER_BRICKS.get())
                .requires(NTItems.WARPED_WART.get(), 2).requires(Items.NETHER_BRICK, 2)
                .unlockedBy(getHasName(NTItems.WARPED_WART.get()), has(NTItems.WARPED_WART.get()))
                .unlockedBy(getHasName(Items.NETHER_BRICK), has(Items.NETHER_BRICK)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, NTItems.HARMONIOUS_CHANGE_STOVE.get())
                .define('A', Items.COPPER_INGOT).define('B', Items.AMETHYST_SHARD)
                .define('C', NTItems.CORUNDUM_IRON_PLATE.get()).define('D', Items.HOPPER)
                .define('E', NTItems.HARMONIOUS_CHANGE_CORE.get()).define('F', Items.BLAST_FURNACE)
                .pattern("ADA").pattern("BEB").pattern("CFC")
                .unlockedBy(getHasName(NTItems.HARMONIOUS_CHANGE_CORE.get()),
                        has(NTItems.HARMONIOUS_CHANGE_CORE.get())).save(recipeOutput);
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
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.TORCH, 4)
                .define('#', Items.STICK)
                .define('X', NTItems.AMBER.get())
                .pattern("X").pattern("#")
                .unlockedBy(getHasName(NTItems.AMBER.get()), has(NTItems.AMBER.get()))
                .save(recipeOutput, NaturalTransmute.prefix("torch_from_amber"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NTItems.DUCK.get()), RecipeCategory.FOOD,
                        NTItems.COOKED_DUCK.get(), 0.35F, 200)
                .unlockedBy(getHasName(NTItems.DUCK.get()), has(NTItems.DUCK.get())).save(recipeOutput);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NTItems.DRAGONCAST_STEEL_BILLET.get()), RecipeCategory.MISC,
                        NTItems.DRAGONCAST_STEEL_INGOT.get(), 0.35F, 200)
                .unlockedBy(getHasName(NTItems.DRAGONCAST_STEEL_BILLET.get()),
                        has(NTItems.DRAGONCAST_STEEL_BILLET.get())).save(recipeOutput);
        // 聚相台配方
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
        // 谐变炉配方
        SpecialRecipeBuilder.special(category -> new HCGrassToFlowerRecipe()).save(recipeOutput,
                NaturalTransmute.prefix("harmonious_change/h_meadow_grass_to_random_flower"));
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.RED_SANDSTONE, Items.STONE, Items.COBBLESTONE,
                Items.DEEPSLATE, Items.COBBLED_DEEPSLATE), Ingredient.of(NTItems.H_DESERT.get()), Items.SANDSTONE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.SANDSTONE, Items.STONE, Items.COBBLESTONE,
                Items.DEEPSLATE, Items.COBBLED_DEEPSLATE), Ingredient.of(NTItems.H_BADLANDS.get()), Items.RED_SANDSTONE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.SANDSTONE), Ingredient.of(NTItems.H_DESERT.get()), Items.SAND, 4, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.MUD), Ingredient.of(NTItems.H_DESERT.get()), Items.CLAY, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.MUD), Ingredient.of(NTItems.H_BADLANDS.get()), Items.DIRT, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.DIRT), Ingredient.of(NTItems.H_DESERT.get()), Items.COARSE_DIRT, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.SUGAR_CANE), Ingredient.of(NTItems.H_DESERT.get()), NTItems.PAPYRUS.get(), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.WET_SPONGE), Ingredient.of(NTItems.H_DESERT.get(), NTItems.H_BADLANDS.get()), Items.SPONGE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.GRAVEL, Items.COARSE_DIRT, Items.RED_SAND), Ingredient.of(NTItems.H_DESERT.get()), Items.SAND, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND), Ingredient.of(Items.CUT_SANDSTONE),
                Ingredient.of(NTItems.H_DESERT.get()), Ingredient.of(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE), 2, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.GRAVEL, Items.COARSE_DIRT, Items.SAND), Ingredient.of(NTItems.H_BADLANDS.get()), Items.RED_SAND, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.GRASS_BLOCK, Items.PODZOL, Items.MYCELIUM, Items.DIRT), Ingredient.of(NTItems.H_BADLANDS.get()), Items.COARSE_DIRT, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.IRON_NUGGET), Ingredient.of(NTItems.H_BADLANDS.get()), Items.IRON_INGOT, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.TURTLE_SCUTE), Ingredient.of(NTItems.H_BADLANDS.get(), NTItems.H_WOODED_BADLANDS.get()), Items.ARMADILLO_SCUTE, 1, 160);
        harmoniousChangeOfOneInputMultipleOutput(recipeOutput, Ingredient.of(Items.RED_SAND), Ingredient.of(NTItems.H_BADLANDS.get()), Ingredient.of(Items.QUARTZ, Items.GOLD_NUGGET), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.SAND, Items.RED_SAND), Ingredient.of(NTItems.H_WOODED_BADLANDS.get()), Items.COARSE_DIRT, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.DEAD_BUSH), Ingredient.of(NTItems.H_WOODED_BADLANDS.get()), Items.OAK_SAPLING, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.DIRT), Ingredient.of(NTItems.H_WOODED_BADLANDS.get(), NTItems.H_FOREST.get(),
                NTItems.H_FLOWER_FOREST.get(), NTItems.H_DARK_FOREST.get(), NTItems.H_SWAMP.get(), NTItems.H_JUNGLE.get()), Items.GRASS_BLOCK, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.WHEAT_SEEDS), Ingredient.of(Items.WHEAT), Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_WOODED_BADLANDS.get(), NTItems.H_PLAINS.get()), Ingredient.of(Items.CARROT), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.CARROT), Ingredient.of(NTItems.H_WOODED_BADLANDS.get(), NTItems.H_MOUNTAINS.get()), Items.POTATO, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.IRON_INGOT), Ingredient.of(NTItems.H_WOODED_BADLANDS.get()), Items.IRON_NUGGET, 10, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.APPLE), Ingredient.of(Items.CACTUS), Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_WOODED_BADLANDS.get()), Ingredient.of(NTItems.PITAYA.get()), 2, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.WATER_BUCKET), Ingredient.of(NTItems.H_SNOWY_TUNDRA.get(), NTItems.H_ICE_SPIKES.get(), NTItems.H_SNOWY_SLOPES.get()), Items.ICE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.MAGMA_BLOCK), Ingredient.of(NTItems.H_SNOWY_TUNDRA.get(), NTItems.H_ICE_SPIKES.get(), NTItems.H_SNOWY_SLOPES.get()), Items.OBSIDIAN, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.SNOW_BLOCK), Ingredient.of(Items.CARVED_PUMPKIN), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_SNOWY_TUNDRA.get(), NTItems.H_ICE_SPIKES.get(), NTItems.H_SNOWY_SLOPES.get()), Ingredient.of(Items.SNOWBALL), 8, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.DIRT), Ingredient.of(NTItemTags.GRASS), Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_SNOWY_TUNDRA.get()), Ingredient.of(Items.GRASS_BLOCK), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.ICE), Ingredient.of(Items.WATER_BUCKET), Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_ICE_SPIKES.get()), Ingredient.of(Items.PACKED_ICE), 1, 160);
        harmoniousChangeOfOneInputMultipleOutput(recipeOutput, Ingredient.of(Items.LAVA_BUCKET), Ingredient.of(NTItems.H_ICE_SPIKES.get()), Ingredient.of(Items.OBSIDIAN), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION), Ingredient.of(NTItems.H_ICE_SPIKES.get()), NTItems.VODKA.get(), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.ICE), Ingredient.of(Items.CALCITE), Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_ICE_SPIKES.get()), Ingredient.of(NTItems.ICELAND_SPAR.get()), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.STONE), Ingredient.of(NTItems.H_MOUNTAINS.get()), Items.COBBLESTONE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.DEEPSLATE), Ingredient.of(NTItems.H_MOUNTAINS.get()), Items.COBBLED_DEEPSLATE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.STONE, Items.COBBLESTONE, Items.DEEPSLATE, Items.COBBLED_DEEPSLATE), Ingredient.of(NTItems.H_MOUNTAINS.get()), Items.GRAVEL, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.STONE_BRICKS, Items.CHISELED_STONE_BRICKS, Items.MOSSY_STONE_BRICKS), Ingredient.of(NTItems.H_MOUNTAINS.get()), Items.CRACKED_STONE_BRICKS, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.WHEAT_SEEDS), Ingredient.of(Items.WHEAT), Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_MOUNTAINS.get()), Ingredient.of(Items.POTATO), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.POWDER_SNOW_BUCKET), Ingredient.of(NTItems.H_SNOWY_SLOPES.get()), Items.ICE, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.BLUE_ICE), Ingredient.of(Items.FIREWORK_ROCKET), Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_SNOWY_SLOPES.get()), Ingredient.of(NTItems.REFRIGERATED_ROCKET.get()), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.RAW_IRON), Ingredient.of(Items.QUARTZ), Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_MEADOW.get()), Ingredient.of(Items.AMETHYST_SHARD), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.BONE), Ingredient.of(Items.STONE), Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_MEADOW.get()), Ingredient.of(Items.CALCITE), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.HONEYCOMB_BLOCK), Ingredient.of(NTItems.H_MOUNTAINS.get(), NTItems.H_FLOWER_FOREST.get()), Items.HONEY_BLOCK, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.HONEY_BLOCK), Ingredient.of(NTItems.H_MOUNTAINS.get(), NTItems.H_FLOWER_FOREST.get()), Items.HONEYCOMB_BLOCK, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.HONEYCOMB), Ingredient.of(Items.GLASS_BOTTLE), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MEADOW.get(), NTItems.H_FLOWER_FOREST.get()), Ingredient.of(Items.HONEY_BOTTLE), 1, 160);
        harmoniousChangeOfOneInputMultipleOutput(recipeOutput, Ingredient.of(Items.HONEY_BOTTLE), Ingredient.of(NTItems.H_MOUNTAINS.get(), NTItems.H_FLOWER_FOREST.get()), Ingredient.of(Items.HONEYCOMB, Items.GLASS_BOTTLE), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.DEAD_BUSH), Ingredient.of(NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get(), NTItems.H_SWAMP.get()), Items.OAK_SAPLING, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.OAK_SAPLING), Ingredient.of(NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get()), Items.BIRCH_SAPLING, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.BIRCH_SAPLING), Ingredient.of(NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get()), Items.OAK_SAPLING, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.OAK_LEAVES), Ingredient.of(NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get()), Items.BIRCH_LEAVES, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.EXPERIENCE_BOTTLE), Ingredient.of(NTItems.H_DARK_FOREST.get()), Items.OMINOUS_BOTTLE, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.LAPIS_LAZULI), Ingredient.of(Items.GOLDEN_APPLE), Ingredient.of(ItemTags.SKULLS),
                Ingredient.of(NTItems.H_DARK_FOREST.get()), Ingredient.of(Items.TOTEM_OF_UNDYING), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.EMERALD), Ingredient.of(Items.BONE), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_DARK_FOREST.get()), Ingredient.of(NTItems.FANGS.get()), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.DEAD_BUSH), Ingredient.of(NTItems.H_BIRCH_FOREST.get()), Items.BIRCH_SAPLING, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.SHORT_GRASS, Items.FERN), Ingredient.of(NTItems.H_MUSHROOM.get()), Items.BROWN_MUSHROOM, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.GRASS_BLOCK), Ingredient.of(NTItems.H_MUSHROOM.get()), Items.MYCELIUM, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.BOWL), Ingredient.of(NTItems.H_MUSHROOM.get()), Items.MUSHROOM_STEW, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.VINE, Items.MOSS_CARPET), Ingredient.of(NTItems.H_MUSHROOM.get()), Items.GLOW_LICHEN, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.BROWN_MUSHROOM), Ingredient.of(Items.GOLDEN_APPLE), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MUSHROOM.get()), Ingredient.of(NTItems.GANODERMA_LUCIDUM.get()), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.BIG_DRIPLEAF, Items.SMALL_DRIPLEAF), Ingredient.of(NTItems.H_SWAMP.get()), Items.LILY_PAD, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.HONEY_BLOCK), Ingredient.of(NTItems.H_SWAMP.get()), Items.SLIME_BLOCK, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.DEAD_BUSH), Ingredient.of(NTItems.H_MANGROVE_SWAMP.get()), Items.MANGROVE_PROPAGULE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.MAGMA_BLOCK), Ingredient.of(NTItems.H_MANGROVE_SWAMP.get()), Items.OCHRE_FROGLIGHT, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.MAGMA_BLOCK), Ingredient.of(Items.BAMBOO_BLOCK, Items.CACTUS), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MANGROVE_SWAMP.get()), Ingredient.of(Items.PEARLESCENT_FROGLIGHT), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.MAGMA_BLOCK), Ingredient.of(Items.SNOW_BLOCK), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MANGROVE_SWAMP.get()), Ingredient.of(Items.VERDANT_FROGLIGHT), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.MAGMA_BLOCK), Ingredient.of(NTItems.HETEROGENEOUS_STONE.get()), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MANGROVE_SWAMP.get()), Ingredient.of(NTItems.AZURE_FROGLIGHT.get()), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.TROPICAL_FISH_BUCKET), Ingredient.of(Items.SLIME_BLOCK), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MANGROVE_SWAMP.get()), Ingredient.of(Items.TADPOLE_BUCKET), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.DIRT), Ingredient.of(NTItems.H_MANGROVE_SWAMP.get()), Items.MUD, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.GLOW_LICHEN), Ingredient.of(NTItems.H_MANGROVE_SWAMP.get()), Items.MOSS_CARPET, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.PORKCHOP), Ingredient.of(NTItems.H_PLAINS.get()), Items.BEEF, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.BEEF), Ingredient.of(NTItems.H_PLAINS.get()), Items.CHICKEN, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.CHICKEN), Ingredient.of(NTItems.H_PLAINS.get()), Items.PORKCHOP, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.COOKED_PORKCHOP), Ingredient.of(NTItems.H_PLAINS.get()), Items.COOKED_BEEF, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.COOKED_BEEF), Ingredient.of(NTItems.H_PLAINS.get()), Items.COOKED_CHICKEN, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.COOKED_CHICKEN), Ingredient.of(NTItems.H_PLAINS.get()), Items.COOKED_PORKCHOP, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(Items.MELON_SEEDS), Ingredient.of(NTItems.H_PLAINS.get()), Items.PUMPKIN_SEEDS, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.GRASS_BLOCK), Ingredient.of(Items.ROTTEN_FLESH), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_JUNGLE.get()), Ingredient.of(Items.PODZOL), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND), Ingredient.of(Items.COBBLESTONE),
                Ingredient.of(NTItems.H_JUNGLE.get()), Ingredient.of(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE), 2, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.WATER_BUCKET), Ingredient.of(Items.BEEF), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_SAVANNA.get()), Ingredient.of(Items.MILK_BUCKET), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(Items.GRASS_BLOCK), Ingredient.of(Items.LEATHER), Ingredient.of(Items.GOLDEN_APPLE),
                Ingredient.of(NTItems.H_SAVANNA.get()), Ingredient.of(NTBlocks.GRASSLAND_EARTH.get()), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.SMALL_FLOWERS), Ingredient.of(NTItems.H_SNOWY_TUNDRA.get()), NTItems.DRYAS_OCTOPETALA.get(), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.SMALL_FLOWERS), Ingredient.of(NTItems.H_MUSHROOM.get()), Items.RED_MUSHROOM, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.FLOWERS), Ingredient.of(NTItems.H_SWAMP.get()), Items.BLUE_ORCHID, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.FLOWERS), Ingredient.of(NTItems.H_JUNGLE.get()), Items.VINE, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(ItemTags.TALL_FLOWERS), Ingredient.of(Items.YELLOW_DYE), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_PLAINS.get()), Ingredient.of(Items.SUNFLOWER), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.OAK_LOGS), Ingredient.of(NTItems.H_SNOWY_TUNDRA.get()), NTItems.DRYAS_OCTOPETALA.get(), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.SAPLINGS), Ingredient.of(NTItems.H_DESERT.get(), NTItems.H_BADLANDS.get()), Items.DEAD_BUSH, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.SAPLINGS), Ingredient.of(NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get(), NTItems.H_SWAMP.get()), Items.OAK_SAPLING, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.SAPLINGS), Ingredient.of(NTItems.H_DARK_FOREST.get()), Items.DARK_OAK_SAPLING, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.SAPLINGS), Ingredient.of(NTItems.H_DARK_FOREST.get()), Items.BIRCH_SAPLING, 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(ItemTags.SAPLINGS), Ingredient.of(Items.GLASS_BOTTLE), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_BIRCH_FOREST.get()), Ingredient.of(NTItems.BOTTLE_OF_TEA.get()), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS), Ingredient.of(Items.JUNGLE_LOG), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_JUNGLE.get()), Ingredient.of(Items.COCOA_BEANS), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.VILLAGER_PLANTABLE_SEEDS), Ingredient.of(NTItems.H_SAVANNA.get()), Items.PUMPKIN_SEEDS, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.SAPLINGS), Ingredient.of(NTItems.H_MANGROVE_SWAMP.get()), Items.MANGROVE_PROPAGULE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.SAPLINGS), Ingredient.of(NTItems.H_PLAINS.get()), Items.MELON_SEEDS, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.SAPLINGS), Ingredient.of(NTItems.H_JUNGLE.get()), Items.JUNGLE_SAPLING, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.LEAVES), Ingredient.of(NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get(), NTItems.H_SWAMP.get()), Items.OAK_LEAVES, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.LEAVES), Ingredient.of(NTItems.H_DARK_FOREST.get()), Items.DARK_OAK_LEAVES, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.LEAVES), Ingredient.of(NTItems.H_BIRCH_FOREST.get()), Items.BIRCH_LEAVES, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(ItemTags.LEAVES), Ingredient.of(NTItems.H_JUNGLE.get()), Items.JUNGLE_LEAVES, 1, 160);
        harmoniousChangeOfOneInputMultipleOutput(recipeOutput, Ingredient.of(ItemTags.BANNERS), Ingredient.of(NTItems.H_DARK_FOREST.get()),
                Ingredient.of(Raid.getLeaderBannerInstance(this.registries.join().lookupOrThrow(Registries.BANNER_PATTERN))), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(ItemTags.CREEPER_DROP_MUSIC_DISCS), Ingredient.of(Items.AMETHYST_SHARD), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MEADOW.get()), Ingredient.of(NTItems.MELODIOUS_DISC.get()), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(NTItemTags.GRASS), Ingredient.of(Items.DIRT), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_MEADOW.get(), NTItems.H_PLAINS.get()), Ingredient.of(Items.GRASS_BLOCK), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(ItemTags.WOOL), Ingredient.of(Items.BEEF), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_SAVANNA.get()), Ingredient.of(Items.LEATHER), 3, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.fromValues(
                Stream.of(new Ingredient.TagValue(NTItemTags.GRASS),
                        new Ingredient.TagValue(ItemTags.SAPLINGS),
                        new Ingredient.TagValue(ItemTags.FLOWERS))),
                Ingredient.of(NTItems.H_SAVANNA.get()), Items.VINE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(NTItemTags.GRASS), Ingredient.of(NTItems.H_SWAMP.get(), NTItems.H_MANGROVE_SWAMP.get()), Items.VINE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(NTItemTags.GRASS), Ingredient.of(NTItems.H_JUNGLE.get()), Items.BAMBOO, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(NTItemTags.COOKED_MEAT), Ingredient.of(NTItems.H_WOODED_BADLANDS.get()), NTItems.DOGFOOD_STRIPED.get(), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(NTItemTags.COOKED_MEAT), Ingredient.of(NTItems.H_FOREST.get()), NTItems.DOGFOOD_WOODS.get(), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(NTItemTags.COOKED_FISH), Ingredient.of(NTItems.H_DESERT.get()), NTItems.CAT_FOOD_PERSIAN.get(), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(NTItemTags.COOKED_FISH), Ingredient.of(NTItems.H_WOODED_BADLANDS.get()), NTItems.CAT_FOOD_RAGDOLL.get(), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(NTItemTags.COOKED_FISH), Ingredient.of(Items.WHITE_DYE), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_FOREST.get()), Ingredient.of(NTItems.CAT_FOOD_WHITE.get()), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(NTItemTags.COOKED_FISH), Ingredient.of(NTItems.H_FOREST.get()), NTItems.CAT_FOOD_BRITISH_SHORTHAIR.get(), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(NTItemTags.COOKED_FISH), Ingredient.of(Items.ORANGE_DYE), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_BIRCH_FOREST.get()), Ingredient.of(NTItems.CAT_FOOD_RED.get()), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(NTItemTags.COOKED_FISH), Ingredient.of(NTItems.H_BIRCH_FOREST.get()), NTItems.CAT_FOOD_TABBY.get(), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(NTItemTags.COOKED_FISH), Ingredient.of(Items.BLACK_DYE), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_PLAINS.get()), Ingredient.of(NTItems.CAT_FOOD_TUXEDO.get()), 1, 160);
        harmoniousChangeOfConsumeInput(recipeOutput, Ingredient.of(NTItemTags.COOKED_FISH), Ingredient.of(Items.BONE), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_PLAINS.get()), Ingredient.of(NTItems.CAT_FOOD_JELLIE.get()), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(NTItemTags.COOKED_FISH), Ingredient.of(NTItems.H_PLAINS.get()), NTItems.CAT_FOOD_CALICO.get(), 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(new ItemStack(Items.SAND, 2)), Ingredient.of(NTItems.H_DESERT.get()), Items.QUARTZ, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(new ItemStack(Items.ROTTEN_FLESH, 2)), Ingredient.of(NTItems.H_WOODED_BADLANDS.get()), Items.BONE, 1, 160);
        harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(new ItemStack(Items.QUARTZ, 2)), Ingredient.of(NTItems.H_SNOWY_TUNDRA.get()), NTItems.TRANSPARENT_CRYSTAL.get(), 1, 160);
        harmoniousChangeOfAnyWoodToOneWood(recipeOutput, Ingredient.of(NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get(), NTItems.H_SWAMP.get()), BlockFamilies.OAK_PLANKS);
        harmoniousChangeOfAnyWoodToOneWood(recipeOutput, Ingredient.of(NTItems.H_MOUNTAINS.get()), BlockFamilies.SPRUCE_PLANKS);
        harmoniousChangeOfAnyWoodToOneWood(recipeOutput, Ingredient.of(NTItems.H_DARK_FOREST.get()), BlockFamilies.DARK_OAK_PLANKS);
        harmoniousChangeOfAnyWoodToOneWood(recipeOutput, Ingredient.of(NTItems.H_BIRCH_FOREST.get()), BlockFamilies.BIRCH_PLANKS);
        harmoniousChangeOfAnyWoodToOneWood(recipeOutput, Ingredient.of(NTItems.H_JUNGLE.get()), BlockFamilies.JUNGLE_PLANKS);
        BlockFamilies.MOSSY_COBBLESTONE.getVariants().forEach((variant, block) -> harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(block),
                Ingredient.of(NTItems.H_DESERT.get(), NTItems.H_BADLANDS.get(), NTItems.H_ICE_SPIKES.get(), NTItems.H_SNOWY_SLOPES.get()), BlockFamilies.COBBLESTONE.get(variant), 1, 160));
        BlockFamilies.MOSSY_STONE_BRICKS.getVariants().forEach((variant, block) -> harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(block),
                Ingredient.of(NTItems.H_DESERT.get(), NTItems.H_BADLANDS.get(), NTItems.H_ICE_SPIKES.get(), NTItems.H_SNOWY_SLOPES.get()), BlockFamilies.STONE_BRICK.get(variant), 1, 160));
        BlockFamilies.COBBLESTONE.getVariants().forEach((variant, block) -> harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(block),
                Ingredient.of(NTItems.H_MEADOW.get(), NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get(),
                        NTItems.H_DARK_FOREST.get(), NTItems.H_BIRCH_FOREST.get(), NTItems.H_SWAMP.get(),
                        NTItems.H_MANGROVE_SWAMP.get(), NTItems.H_JUNGLE.get()),
                BlockFamilies.MOSSY_COBBLESTONE.get(variant), 1, 160));
        BlockFamilies.STONE_BRICK.getVariants().forEach((variant, block) -> {
            BlockFamily mossyStoneBricks = BlockFamilies.MOSSY_STONE_BRICKS;
            if (mossyStoneBricks.getVariants().containsKey(variant)) {
                harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(block), Ingredient.of(
                        NTItems.H_MEADOW.get(), NTItems.H_FOREST.get(), NTItems.H_FLOWER_FOREST.get(),
                                NTItems.H_DARK_FOREST.get(), NTItems.H_BIRCH_FOREST.get(), NTItems.H_SWAMP.get(),
                                NTItems.H_MANGROVE_SWAMP.get(), NTItems.H_JUNGLE.get()),
                        mossyStoneBricks.get(variant), 1, 160);
            }
        });
        BlockFamilies.OAK_PLANKS.getVariants().forEach((variant, block) -> harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(block),
                Ingredient.of(NTItems.H_FOREST.get()), BlockFamilies.BIRCH_PLANKS.get(variant), 1, 160));
        BuiltInRegistries.ITEM.stream().filter(item -> getItemName(item).endsWith("_glazed_terracotta")).forEach(item -> {
            Item newItem = BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(getItemName(item).replaceAll("_glazed", (""))));
            harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(item), Ingredient.of(NTItems.H_DESERT.get(), NTItems.H_BADLANDS.get()), newItem, 1, 160);
        });
        BuiltInRegistries.BLOCK.stream().filter(block -> block instanceof FlowerBlock).forEach(block -> {
            SuspiciousEffectHolder holder = SuspiciousEffectHolder.tryGet(block);
            ItemStack itemStack = new ItemStack(Items.SUSPICIOUS_STEW);
            SuspiciousStewEffects stewEffects = holder != null ?
                    holder.getSuspiciousEffects(): SuspiciousStewEffects.EMPTY;
            itemStack.set(DataComponents.SUSPICIOUS_STEW_EFFECTS, stewEffects);
            harmoniousChangeOfOneInputMultipleOutput(recipeOutput, Ingredient.of(block),
                    Ingredient.of(NTItems.H_MUSHROOM.get()),
                    Ingredient.of(itemStack), 1, 160);
        });
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block instanceof InfestedBlock infestedBlock) {
                harmoniousChangeOfOneInputOneOutput(recipeOutput,
                        Ingredient.of(infestedBlock.getHostBlock(), Items.SPIDER_EYE),
                        Ingredient.of(NTItems.H_MOUNTAINS.get(), NTItems.H_SNOWY_SLOPES.get()),
                        infestedBlock.asItem(), 1, 160);
            }
        });
        Map<String, String> stoneTransferMap = Map.of("andesite", "diorite",
                "diorite", "granite", "granite", "andesite");
        List<BlockFamily> stoneBlockFamilies = List.of(
                BlockFamilies.ANDESITE, BlockFamilies.POLISHED_ANDESITE,
                BlockFamilies.DIORITE, BlockFamilies.POLISHED_DIORITE,
                BlockFamilies.GRANITE, BlockFamilies.POLISHED_GRANITE);
        stoneBlockFamilies.forEach(blockFamily -> {
            Collection<Block> blocks = blockFamily.getVariants().values();
            List<Block> blockList = new ArrayList<>(blocks);
            blockList.add(blockFamily.getBaseBlock());
            blockList.forEach(block -> {
                String name = getItemName(block);
                for (String s : stoneTransferMap.keySet()) {
                    if (name.contains(s)) {
                        name = name.replaceAll(s, stoneTransferMap.get(s)); break;
                    }
                }

                Item newBlock = BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(name));
                harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(block), Ingredient.of(NTItems.H_MOUNTAINS.get(),
                        NTItems.H_SNOWY_SLOPES.get(), NTItems.H_MEADOW.get()), newBlock, 1, 160);
            });
        });
        harmoniousChangeOfOneInputMultipleOutput(recipeOutput, Ingredient.fromValues(BuiltInRegistries.BLOCK
                        .stream().filter(block -> block instanceof InfestedBlock)
                        .map(block -> block.asItem().getDefaultInstance()).map(Ingredient.ItemValue::new)),
                Ingredient.of(NTItems.H_MOUNTAINS.get(), NTItems.H_SNOWY_SLOPES.get()),
                Ingredient.of(Items.GRAVEL, NTItems.SILVERFISH_PUPA.get()), 1, 160);
        WaterWax.CAN_USE_WATER_WAX.get().forEach((b1, b2) -> harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(b2),
                Ingredient.of(NTItems.H_DESERT.get(), NTItems.H_BADLANDS.get()), b1.asItem(), 1, 160));
        this.buildArmorRecipes(recipeOutput, NTItems.DRAGONCAST_HELMET.get(), NTItems.DRAGONCAST_CHESTPLATE.get(),
                NTItems.DRAGONCAST_LEGGINGS.get(), NTItems.DRAGONCAST_BOOTS.get(), NTItems.DRAGONCAST_STEEL_INGOT.get());
        this.buildBaseToolRecipes(recipeOutput, NTItems.SCULK_BONE_SWORD.get(), NTItems.SCULK_BONE_PICKAXE.get(), NTItems.SCULK_BONE_SHOVEL.get(),
                NTItems.SCULK_BONE_AXE.get(), NTItems.SCULK_BONE_HOE.get(), Blocks.DEEPSLATE.asItem(), NTItems.SCULK_BONE.get());
        NTBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(blockFamily ->
                generateRecipes(recipeOutput, blockFamily, FeatureFlagSet.of(FeatureFlags.VANILLA)));
    }

    @Override
    protected @NotNull CompletableFuture<?> run(CachedOutput output, HolderLookup.Provider registries) {
        final Set<ResourceLocation> set = Sets.newHashSet();
        final List<CompletableFuture<?>> list = new ArrayList<>();
        this.buildRecipes(new NTRecipeOutput(this.recipePathProvider,
                this.advancementPathProvider, output, registries, set, list));
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    public static void gathering(RecipeOutput recipeOutput, Ingredient input1, Ingredient input2, Item core, Item result, int gatheringTime) {
        GatheringRecipeBuilder.addRecipe(input1, input2, core, result, gatheringTime).unlockedBy(getHasName(core), has(core)).save(recipeOutput);
    }

    public static void gathering(RecipeOutput recipeOutput, ItemLike input1, ItemLike input2, Item core, Item result, int gatheringTime) {
        gathering(recipeOutput, Ingredient.of(input1), Ingredient.of(input2), core, result, gatheringTime);
    }

    public static void harmoniousChange(RecipeOutput recipeOutput, Ingredient input1, Ingredient input2, Ingredient input3, Ingredient fuXiang, Ingredient results, int amount, int time, boolean shouldConsume) {
        HarmoniousChangeRecipeBuilder.addRecipe(input1, input2, input3, fuXiang, results, amount, time, shouldConsume).unlockedBy("has_fu_xiang", has(NTItemTags.FU_XIANG))
                .save(recipeOutput, NaturalTransmute.prefix("harmonious_change_" + harmoniousChangeRecipeId));
        harmoniousChangeRecipeId++;
    }

    public static void harmoniousChange(RecipeOutput recipeOutput, Ingredient input, Ingredient fuXiang, Ingredient result, int amount, int time, boolean shouldConsume) {
        harmoniousChange(recipeOutput, input, Ingredient.of(Items.AIR), Ingredient.of(Items.AIR), fuXiang, result, amount, time, shouldConsume);
    }

    public static void harmoniousChange(RecipeOutput recipeOutput, Ingredient input, Ingredient fuXiang, ItemLike result, int amount, int time, boolean shouldConsume) {
        harmoniousChange(recipeOutput, input, Ingredient.of(Items.AIR), Ingredient.of(Items.AIR), fuXiang, Ingredient.of(result), amount, time, shouldConsume);
    }

    public static void harmoniousChangeOfConsumeInput(RecipeOutput recipeOutput, Ingredient input1, Ingredient input2, Ingredient input3, Ingredient fuXiang, Ingredient results, int amount, int time) {
        harmoniousChange(recipeOutput, input1, input2, input3, fuXiang, results, amount, time, Boolean.TRUE);
    }

    public static void harmoniousChangeOfOneInputMultipleOutput(RecipeOutput recipeOutput, Ingredient input, Ingredient fuXiang, Ingredient result, int amount, int time) {
        harmoniousChangeOfConsumeInput(recipeOutput, input, Ingredient.of(Items.AIR), Ingredient.of(Items.AIR), fuXiang, result, amount, time);
    }

    public static void harmoniousChangeOfOneInputOneOutput(RecipeOutput recipeOutput, Ingredient input, Ingredient fuXiang, ItemLike result, int amount, int time) {
        harmoniousChangeOfConsumeInput(recipeOutput, input, Ingredient.of(Items.AIR), Ingredient.of(Items.AIR), fuXiang, Ingredient.of(result), amount, time);
    }

    public static void harmoniousChangeOfAnyWoodToOneWood(RecipeOutput recipeOutput, Ingredient fuXiang, BlockFamily targetFamily) {
        WOOD_BLOCK_FAMILIES.stream().filter(blockFamily -> blockFamily != BlockFamilies.SPRUCE_PLANKS).forEach(blockFamily -> blockFamily.getVariants().forEach(
                (variant, block) -> harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(block), fuXiang, BlockFamilies.SPRUCE_PLANKS.get(variant), 1, 160)));
        WOOD_BASE_BLOCKS.stream().filter(block -> block != targetFamily.getBaseBlock()).forEach(
                block -> harmoniousChangeOfOneInputOneOutput(recipeOutput, Ingredient.of(block), fuXiang, targetFamily.getBaseBlock(), 1, 160));
    }

    private static void waterWaxRecipes(RecipeOutput recipeOutput) {
        HoneycombItem.WAXABLES.get().forEach((b1, b2) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, b2)
                .requires(b1).requires(NTItems.WATER_WAX.get()).group(getItemName(b2)).unlockedBy(getHasName(b1), has(b1))
                .save(recipeOutput, NaturalTransmute.prefix("water_wax/" + getConversionRecipeName(b2, NTItems.WATER_WAX.get()))));
        WaterWax.CAN_USE_WATER_WAX.get().forEach((b1, b2) -> {
            if (!(b2 instanceof ActivatedCoralWallFan)) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, b2)
                        .requires(b1).requires(NTItems.WATER_WAX.get())
                        .group(getItemName(b2)).unlockedBy(getHasName(b1), has(b1))
                        .save(recipeOutput, NaturalTransmute.prefix("water_wax/" +
                                getConversionRecipeName(b2, NTItems.WATER_WAX.get())));
            }
        });
    }

    private void buildArmorRecipes(
            RecipeOutput recipeOutput, ItemLike helmet, ItemLike chestplate,
            ItemLike leggings, ItemLike boots, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet).define('X', material)
                .pattern("XXX").pattern("X X")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate).define('X', material)
                .pattern("X X").pattern("XXX").pattern("XXX")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings).define('X', material)
                .pattern("XXX").pattern("X X").pattern("X X")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots).define('X', material)
                .pattern("X X").pattern("X X")
                .unlockedBy(getHasName(material), has(material)).save(recipeOutput);
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
                    this.list.add(DataProvider.saveStable(
                            this.output, this.registries, Advancement.CONDITIONAL_CODEC,
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