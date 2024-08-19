package com.zg.natural_transmute.utils;

import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Collectors;

public class NTCommonUtils {

    public static Iterable<Block> getKnownBlocks() {
        return NTBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());
    }

    public static Iterable<Item> getKnownItems() {
        return NTItems.ITEMS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());
    }

}