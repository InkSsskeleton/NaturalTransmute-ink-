package com.zg.natural_transmute.common.blocks;

import com.mojang.serialization.MapCodec;
import com.zg.natural_transmute.common.blocks.entity.HarmoniousChangeStoveBlockEntity;
import com.zg.natural_transmute.common.blocks.state.properties.HCStovePart;
import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class HarmoniousChangeStove extends BaseEntityBlockWithState {

    public static final EnumProperty<HCStovePart> PART = EnumProperty.create("stove_part", HCStovePart.class);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public HarmoniousChangeStove() {
        super(Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().pushReaction(PushReaction.IGNORE));
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, HCStovePart.MAIN)
                .setValue(HALF, DoubleBlockHalf.LOWER).setValue(LIT, Boolean.FALSE));
    }

    @Override
    protected MapCodec<? extends HarmoniousChangeStove> codec() {
        return simpleCodec(p -> new HarmoniousChangeStove());
    }

    @Override
    protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.is(this) && neighborState.getValue(HALF) != doubleBlockHalf ? state.setValue(FACING, neighborState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : state;
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            level.setBlockAndUpdate(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER));
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        HarmoniousChangeStoveBlockEntity blockEntity = this.getController(pos, level);
        if (blockEntity != null) {
            return blockEntity.getBlockState().getBlock().getCloneItemStack(level, pos, state);
        }

        return super.getCloneItemStack(state, target, level, pos, player);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        HarmoniousChangeStoveBlockEntity blockEntity = this.getController(pos, level);
        if (blockEntity != null) {
            blockEntity.getComponents().stream().filter(componentPos -> !componentPos.equals(pos))
                    .forEach(componentPos -> level.removeBlock(componentPos, false));
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(state.getMenuProvider(level, pos), extraData -> extraData.writeBlockPos(pos));
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof HarmoniousChangeStoveBlockEntity stoveBlockEntity) {
                Containers.dropContents(level, pos, stoveBlockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, HALF, LIT);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == HCStovePart.MAIN && state.getValue(HALF) == DoubleBlockHalf.LOWER ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HarmoniousChangeStoveBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, NTBlockEntityTypes.HARMONIOUS_CHANGE_STOVE.get(), HarmoniousChangeStoveBlockEntity::serverTick);
    }

    @Nullable
    protected HarmoniousChangeStoveBlockEntity getController(BlockPos centerPos, Level level) {
        return this.findController(centerPos, level);
    }

    @Nullable
    protected HarmoniousChangeStoveBlockEntity getController(BlockPos centerPos, BlockGetter getter) {
        return this.findController(centerPos, getter);
    }

    @Nullable
    private <T extends BlockGetter> HarmoniousChangeStoveBlockEntity findController(BlockPos centerPos, T blockGetter) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                BlockEntity blockEntity = blockGetter.getBlockEntity(centerPos.offset(x, 1, y));
                if (blockEntity instanceof HarmoniousChangeStoveBlockEntity stoveBlockEntity && stoveBlockEntity.isPartOf(centerPos)) {
                    return stoveBlockEntity;
                }
            }
        }

        return null;
    }

}