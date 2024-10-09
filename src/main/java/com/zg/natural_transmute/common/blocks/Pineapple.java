package com.zg.natural_transmute.common.blocks;

import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class Pineapple extends SweetBerryBushBlock {

    public Pineapple() {
        super(Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH));
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return new ItemStack(NTItems.PINEAPPLE);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {}

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return InteractionResult.PASS;
    }

}