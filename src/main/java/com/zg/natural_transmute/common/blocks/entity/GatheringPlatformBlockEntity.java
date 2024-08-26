package com.zg.natural_transmute.common.blocks.entity;

import com.zg.natural_transmute.client.inventory.GatheringPlatformMenu;
import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;

public class GatheringPlatformBlockEntity extends SimpleContainerBlockEntity {

    public int gatheringTime;
    public int currentState;
    private final ContainerData containerData = new Data();

    public GatheringPlatformBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.GATHERING_PLATFORM.get(), pos, blockState);
        this.handler = new Handler(3);
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.gatheringTime = tag.getInt("GatheringTime");
        this.currentState = tag.getInt("CurrentState");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("GatheringTime", this.gatheringTime);
        tag.putInt("CurrentState", this.currentState);
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        ContainerLevelAccess access = ContainerLevelAccess.create(this.level, this.worldPosition);
        return new GatheringPlatformMenu(containerId, inventory, access, this.handler, this.containerData);
    }

    private class Data implements ContainerData {

        @Override
        public int get(int index) {
            if (index == 0) {
                return gatheringTime;
            } else if (index == 1) {
                return currentState;
            } else {
                return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                gatheringTime = value;
            } else if (index == 1) {
                currentState = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

}