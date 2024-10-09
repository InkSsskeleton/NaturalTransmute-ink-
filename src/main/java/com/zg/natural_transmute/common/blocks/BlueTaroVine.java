package com.zg.natural_transmute.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BlueTaroVine extends VineBlock {

    public static final BooleanProperty FLOWERING = BooleanProperty.create("flowering");
    public static final IntegerProperty AGE = BlockStateProperties.AGE_25;

    public BlueTaroVine() {
        super(Properties.ofFullCopy(Blocks.VINE));
        this.registerDefaultState(this.stateDefinition.any().setValue(FLOWERING, Boolean.FALSE).setValue(AGE, 0));
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (!state.getValue(FLOWERING)) {
            int age = state.getValue(AGE);
            if (age == 25) {
                BlockState newState = state.setValue(FLOWERING, Boolean.TRUE);
                level.setBlockAndUpdate(pos, newState);
                level.neighborChanged(newState, pos, this, pos, Boolean.FALSE);
            } else {
                level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_INVISIBLE);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FLOWERING, AGE);
    }

}