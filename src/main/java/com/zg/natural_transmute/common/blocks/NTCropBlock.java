package com.zg.natural_transmute.common.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BeetrootBlock;

import java.util.function.Supplier;

public class NTCropBlock extends BeetrootBlock {

    private final Supplier<Item> cloneItem;

    public NTCropBlock(Properties properties, Supplier<Item> cloneItem) {
        super(properties);
        this.cloneItem = cloneItem;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return this.cloneItem.get();
    }

}