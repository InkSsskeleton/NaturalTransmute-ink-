package com.zg.natural_transmute.common.items.crafting;

import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class HCPotionToHoneyWineRecipe extends HarmoniousChangeRecipe {

    public HCPotionToHoneyWineRecipe() {
        super(Ingredient.of(Items.HONEY_BOTTLE), Ingredient.of(Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION),
                Ingredient.of(Items.AIR), Ingredient.of(NTItems.H_FROZEN_OCEAN.get()),
                Ingredient.of(NTItems.HONEY_WINE.get()), 2, 160, Boolean.TRUE);
    }

    @Override
    public boolean matches(HarmoniousChangeRecipeInput input, Level level) {
        boolean b1 = false, b2 = false, b3 = false;
        for (int i = 0; i < 3; i++) {
            ItemStack item = input.getItem(i);
            PotionContents contents = item.get(DataComponents.POTION_CONTENTS);
            if (contents != null && contents == PotionContents.EMPTY) {
                return false;
            }

            if (this.input1.test(item)) {
                b1 = true;
            } else if (this.input2.test(item)) {
                b2 = true;
            } else if (this.input3.test(item)) {
                b3 = true;
            }
        }

        boolean flagB = b1 && b2 && b3;
        boolean hasFuel = HarmoniousChangeStoveBlockEntity.getFuel()
                .containsKey(input.getItem(3).getItem());
        return this.fuXiang.test(input.getItem(4)) && hasFuel && flagB;
    }

    @Override
    public Ingredient getInput2() {
        Stream<ItemStack> stackStream1 = BuiltInRegistries.POTION.stream().map(potion ->
                PotionContents.createItemStack(Items.POTION, Holder.direct(potion)));
        Stream<ItemStack> stackStream2 = BuiltInRegistries.POTION.stream().map(potion ->
                PotionContents.createItemStack(Items.SPLASH_POTION, Holder.direct(potion)));
        Stream<ItemStack> stackStream3 = BuiltInRegistries.POTION.stream().map(potion ->
                PotionContents.createItemStack(Items.LINGERING_POTION, Holder.direct(potion)));
        List<ItemStack> list = new ArrayList<>();
        list.addAll(stackStream1.toList());
        list.addAll(stackStream2.toList());
        list.addAll(stackStream3.toList());
        return Ingredient.of(list.stream());
    }

}