package com.zg.natural_transmute.common.data.provider.tag;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class NTItemTagProvider extends ItemTagsProvider {

    public NTItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, NaturalTransmute.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(Tags.Items.TOOLS_BOW).add(NTItems.WHALE_BONE_BOW.get());
        NTCommonUtils.getKnownItems().forEach(item -> {
            ItemStack stack = item.getDefaultInstance();
            if (stack.has(NTDataComponents.CAT_FOODS)) {
                this.tag(ItemTags.CAT_FOOD).add(item);
            }

            if (stack.has(NTDataComponents.DOG_FOODS)) {
                this.tag(ItemTags.WOLF_FOOD).add(item);
            }
        });
    }

}