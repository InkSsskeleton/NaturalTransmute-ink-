package com.zg.natural_transmute.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class Papyrus extends SugarCaneBlock {

    public static final BooleanProperty TOP = BooleanProperty.create("top");

    public Papyrus() {
        super(Properties.ofFullCopy(Blocks.SUGAR_CANE));
        this.registerDefaultState(this.stateDefinition.any().setValue(TOP, Boolean.FALSE));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isEmptyBlock(pos.above())) {
            int i = 1;
            while (level.getBlockState(pos.below(i)).is(this)) {
                i++;
            }

            if (i < 5) {
                int j = state.getValue(AGE);
                if (j == 10) {
                    level.setBlockAndUpdate(pos.above(), this.defaultBlockState().setValue(TOP, Boolean.TRUE));
                    level.setBlock(pos, state.setValue(AGE, 0), 4);
                } else {
                    level.setBlock(pos, state.setValue(AGE, j + 1), 4);
                }
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, TOP);
    }

}