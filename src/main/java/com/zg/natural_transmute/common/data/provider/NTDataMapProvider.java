package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.common.datamaps.HarmoniousChangeFuel;
import com.zg.natural_transmute.registry.NTDataMaps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

/** @noinspection deprecation*/
public class NTDataMapProvider extends DataMapProvider {

    public NTDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        Builder<HarmoniousChangeFuel, Item> hcFuels = this.builder(NTDataMaps.HARMONIOUS_CHANGE_FUELS);
        HarmoniousChangeStoveBlockEntity.buildFuels((value, amount) -> value
                .ifLeft(item -> hcFuels.add(item.builtInRegistryHolder(), new HarmoniousChangeFuel(amount), false))
                .ifRight(tag -> hcFuels.add(tag, new HarmoniousChangeFuel(amount), false)));
    }

}