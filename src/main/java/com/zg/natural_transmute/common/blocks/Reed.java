package com.zg.natural_transmute.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

public class Reed extends DoublePlantBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public Reed() {
        super(Properties.ofFullCopy(Blocks.TALL_GRASS));
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            if (level.getBlockState(pos).getFluidState().is(FluidTags.WATER)) {
                return true;
            }

            if (belowState.is(BlockTags.DIRT) || belowState.is(BlockTags.SAND)) {
                BlockPos belowPos = pos.below();
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    BlockState blockState = level.getBlockState(belowPos.relative(direction));
                    FluidState fluidState = level.getFluidState(belowPos.relative(direction));
                    if (state.canBeHydrated(level, pos, fluidState, belowPos.relative(direction)) || blockState.is(Blocks.FROSTED_ICE)) {
                        return true;
                    }
                }
            }

            return super.canSurvive(state, level, pos);
        } else {
            if (state.getBlock() != this) {
                return super.canSurvive(state, level, pos);
            }

            return belowState.is(this) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = super.getStateForPlacement(context);
        if (state != null && state.hasProperty(WATERLOGGED)) {
            state = state.setValue(WATERLOGGED, fluidState.is(FluidTags.WATER));
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, WATERLOGGED);
    }

}