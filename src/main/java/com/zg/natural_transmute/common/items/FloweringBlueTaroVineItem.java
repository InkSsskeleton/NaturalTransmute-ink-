package com.zg.natural_transmute.common.items;

import com.zg.natural_transmute.common.blocks.BlueTaroVine;
import com.zg.natural_transmute.registry.NTBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FloweringBlueTaroVineItem extends BlockItem {
    
    public FloweringBlueTaroVineItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected @Nullable BlockState getPlacementState(BlockPlaceContext context) {
        return NTBlocks.BLUE_TARO_VINE.get().defaultBlockState().setValue(BlueTaroVine.FLOWERING, Boolean.TRUE);
    }
    
}