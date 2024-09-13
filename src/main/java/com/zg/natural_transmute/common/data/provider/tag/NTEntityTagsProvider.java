package com.zg.natural_transmute.common.data.provider.tag;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class NTEntityTagsProvider extends EntityTypeTagsProvider {

    public NTEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, NaturalTransmute.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES).add(NTEntityTypes.LAVA_AXOLOTL.get());
    }

}