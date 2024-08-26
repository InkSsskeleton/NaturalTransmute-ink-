package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.common.data.loot.NTBlockLoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class NTLootTableProvider extends LootTableProvider {

    public NTLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Set.of(), List.of(new SubProviderEntry(NTBlockLoot::new, LootContextParamSets.BLOCK)), provider);
    }

}