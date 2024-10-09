package com.zg.natural_transmute.common.items.crafting;

import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class HCBannerToLeaderBannerRecipe extends HarmoniousChangeRecipe {

    public HCBannerToLeaderBannerRecipe() {
        super(Ingredient.of(ItemTags.BANNERS), Ingredient.of(Items.AIR), Ingredient.of(Items.AIR),
                Ingredient.of(NTItems.H_DARK_FOREST.get()), Ingredient.of(Items.WHITE_BANNER), 1, 160, Boolean.TRUE);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return Raid.getLeaderBannerInstance(registries.lookupOrThrow(Registries.BANNER_PATTERN));
    }

}