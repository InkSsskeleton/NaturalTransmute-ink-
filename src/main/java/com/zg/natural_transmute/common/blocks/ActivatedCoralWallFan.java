package com.zg.natural_transmute.common.blocks;

import com.zg.natural_transmute.common.items.WaterWax;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CoralWallFanBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.HitResult;

public class ActivatedCoralWallFan extends CoralWallFanBlock {

    private final Item blockItem;

    public ActivatedCoralWallFan(Block deadBlock, MapColor mapColor, Item blockItem) {
        super(deadBlock, Properties.of().mapColor(mapColor).noCollission().instabreak().sound(SoundType.WET_GRASS));
        this.registerDefaultState(this.defaultBlockState().setValue(WaterWax.NO_DIE, Boolean.FALSE));
        this.blockItem = blockItem;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(this.blockItem);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, WaterWax.NO_DIE);
    }

}