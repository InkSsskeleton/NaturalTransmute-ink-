package com.zg.natural_transmute.common.data.provider.tag;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class NTBlockTagsProvider extends BlockTagsProvider {

    public NTBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NaturalTransmute.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(NTBlocks.GATHERING_PLATFORM.get(),
                NTBlocks.PAPYRUS.get(), NTBlocks.CORUNDUM.get());
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(NTBlocks.TURQUOISE.get());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(NTBlocks.CORUNDUM.get());
    }

}