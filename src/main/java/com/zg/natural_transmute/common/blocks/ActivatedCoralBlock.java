package com.zg.natural_transmute.common.blocks;

import com.zg.natural_transmute.common.items.WaterWax;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.HitResult;

public class ActivatedCoralBlock extends CoralBlock {

    private final Item blockItem;

    public ActivatedCoralBlock(Block deadBlock, MapColor mapColor, Item blockItem) {
        super(deadBlock, Properties.of().instrument(NoteBlockInstrument.BASEDRUM).mapColor(mapColor)
                .requiresCorrectToolForDrops().strength((1.5F), (6.0F)).sound(SoundType.CORAL_BLOCK));
        this.registerDefaultState(this.stateDefinition.any().setValue(WaterWax.NO_DIE, Boolean.FALSE));
        this.blockItem = blockItem;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(this.blockItem);
    }

    @Override
    public Item asItem() {
        return this.blockItem;
    }

    @Override
    protected boolean scanForWater(BlockGetter level, BlockPos pos) {
        return !level.getBlockState(pos).getValue(WaterWax.NO_DIE) && super.scanForWater(level, pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WaterWax.NO_DIE);
    }

}