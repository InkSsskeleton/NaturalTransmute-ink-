package com.zg.natural_transmute.common.blocks;

import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class WarpedWart extends NetherWartBlock {

    public WarpedWart() {
        super(Properties.ofFullCopy(Blocks.NETHER_WART));
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return new ItemStack(NTItems.WARPED_WART);
    }

}