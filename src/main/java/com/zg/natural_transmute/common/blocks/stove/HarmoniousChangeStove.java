package com.zg.natural_transmute.common.blocks.stove;

import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.common.blocks.state.properties.HCStovePart;
import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;

public class HarmoniousChangeStove extends HCStoveBase {

    public HarmoniousChangeStove() {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos mainPos = context.getClickedPos();
        BlockState blockState = super.getStateForPlacement(context);
        if (blockState == null) return null;
        Direction facing = blockState.getValue(FACING).getCounterClockWise();
        BlockPos backPos = mainPos.relative(facing.getOpposite());
        BlockPos sidePos = mainPos.relative(facing.getCounterClockWise());
        BlockPos diagonalPos = sidePos.relative(facing.getOpposite());
        BlockPos topPos = diagonalPos.above();
        boolean placeable = this.canPlace(level, backPos, sidePos, diagonalPos, topPos);
        return placeable ? blockState : null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide()) {
            Direction facing = state.getValue(FACING).getCounterClockWise();
            BlockPos backPos = pos.relative(facing.getOpposite());
            BlockPos sidePos = pos.relative(facing.getCounterClockWise());
            BlockPos diagonalPos = sidePos.relative(facing.getOpposite());
            BlockPos topPos = diagonalPos.above();
            if (this.canPlace(level, backPos, sidePos, diagonalPos, topPos)) {
                level.setBlock(backPos, this.defaultBlockState().setValue(PART, HCStovePart.MAIN_HEAD).setValue(FACING, facing), 3);
                level.setBlock(sidePos, this.defaultBlockState().setValue(PART, HCStovePart.RIGHT).setValue(FACING, facing), 3);
                level.setBlock(diagonalPos, this.defaultBlockState().setValue(PART, HCStovePart.RIGHT_HEAD).setValue(FACING, facing), 3);
                if (level.getBlockEntity(pos) instanceof HarmoniousChangeStoveBlockEntity blockEntity) {
                    blockEntity.setComponents(pos, backPos, sidePos, diagonalPos);
                }
            }
        }
    }

    private boolean canPlace(Level level, BlockPos... blockPoses) {
        for (BlockPos blockPos : blockPoses) {
            if (!level.getBlockState(blockPos).canBeReplaced()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, NTBlockEntityTypes.HARMONIOUS_CHANGE_STOVE.get(), HarmoniousChangeStoveBlockEntity::serverTick);
    }

    @SuppressWarnings("unchecked")
    private @Nullable static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>)ticker : null;
    }

}