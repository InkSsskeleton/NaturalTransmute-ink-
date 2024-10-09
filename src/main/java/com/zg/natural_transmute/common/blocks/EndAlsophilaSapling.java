package com.zg.natural_transmute.common.blocks;

import com.zg.natural_transmute.common.data.tags.NTBlockTags;
import com.zg.natural_transmute.registry.NTTreeGrowers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class EndAlsophilaSapling extends SaplingBlock {

    public EndAlsophilaSapling() {
        super(NTTreeGrowers.END_ALSOPHILA, ofFullCopy(Blocks.OAK_SAPLING));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(NTBlockTags.END_ALSOPHILA_SAPLING_PLACEABLE);
    }

}