package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
public class NTRecipeProvider extends RecipeProvider {

    public NTRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 6)
                .requires(NTItems.WHALE_BONE.get()).group("bonemeal")
                .unlockedBy("has_whale_bone", has(NTItems.WHALE_BONE.get()))
                .save(recipeOutput, NaturalTransmute.prefix("whale_bone_to_bonemeal"));
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
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(NTItems.DUCK.get()), RecipeCategory.FOOD,
                        NTItems.COOKED_DUCK.get(), 0.35F, 200)
                .unlockedBy("has_chicken", has(NTItems.DUCK.get())).save(recipeOutput);
        this.buildBaseToolRecipes(recipeOutput, NTItems.SCULK_BONE_SWORD.get(),
                NTItems.SCULK_BONE_PICKAXE.get(), NTItems.SCULK_BONE_SHOVEL.get(),
                NTItems.SCULK_BONE_AXE.get(), NTItems.SCULK_BONE_HOE.get(),
                Blocks.DEEPSLATE.asItem(), NTItems.SCULK_BONE.get());
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

}