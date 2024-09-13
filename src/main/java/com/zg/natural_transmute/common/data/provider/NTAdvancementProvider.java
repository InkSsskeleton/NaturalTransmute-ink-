package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.common.data.advancements.NTAdvancements;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class NTAdvancementProvider {

    public static AdvancementProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new AdvancementProvider(output, registries, List.of(new NTAdvancements()));
    }

}