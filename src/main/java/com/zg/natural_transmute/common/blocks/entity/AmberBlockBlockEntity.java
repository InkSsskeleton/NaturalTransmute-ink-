package com.zg.natural_transmute.common.blocks.entity;

import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class AmberBlockBlockEntity extends SimpleContainerBlockEntity {

    public AmberBlockBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.AMBER_BLOCK.get(), pos, blockState);
        this.handler = new Handler(1);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return null;
    }

}