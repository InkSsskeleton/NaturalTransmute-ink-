package com.zg.natural_transmute.common.blocks.entity;

import com.zg.natural_transmute.client.inventory.HarmoniousChangeStoveMenu;
import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class HarmoniousChangeStoveBlockEntity extends SimpleContainerBlockEntity {

    private int harmoniousChangeTime;
    private int maxHarmoniousChangeTime;
    private int currentState;
    private final ContainerData containerData = new Data();
    @NotNull
    private Set<BlockPos> components = new HashSet<>(8);

    public HarmoniousChangeStoveBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.HARMONIOUS_CHANGE_STOVE.get(), pos, blockState);
        this.handler = new Handler(8);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HarmoniousChangeStoveBlockEntity blockEntity) {
        if (blockEntity.harmoniousChangeTime > 0) {
            blockEntity.currentState = 1;
            setChanged(level, pos, state);
        } else {
            blockEntity.currentState = 0;
            setChanged(level, pos, state);
        }
    }

    public void setComponents(BlockPos... components) {
        if (components.length != 8) {
            return;
        }

        this.components.addAll(Arrays.asList(components));
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean isPartOf(BlockPos blockPos) {
        return this.components.contains(blockPos);
    }

    public @NotNull Set<BlockPos> getComponents() {
        return this.components;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.harmoniousChangeTime = tag.getInt("HarmoniousChangeTime");
        this.maxHarmoniousChangeTime = tag.getInt("MaxHarmoniousChangeTime");
        this.currentState = tag.getInt("CurrentState");
        this.components = readBlockPoses(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("HarmoniousChangeTime", this.harmoniousChangeTime);
        tag.putInt("MaxHarmoniousChangeTime", this.maxHarmoniousChangeTime);
        tag.putInt("CurrentState", this.currentState);
        if (!this.components.isEmpty()) {
            putBlockPoses(tag, this.components);
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        ContainerLevelAccess access = ContainerLevelAccess.create(this.level, this.worldPosition);
        return new HarmoniousChangeStoveMenu(containerId, inventory, access, this.handler, this.containerData);
    }

    public static void putBlockPoses(CompoundTag compoundTag, Collection<BlockPos> blockPoses) {
        if (!blockPoses.isEmpty()) {
            int[] positions = new int[blockPoses.size() * 3];
            int pos = 0;
            for (BlockPos blockPos : blockPoses) {
                positions[pos * 3] = blockPos.getX();
                positions[pos * 3 + 1] = blockPos.getY();
                positions[pos * 3 + 2] = blockPos.getZ();
                pos++;
            }

            compoundTag.putIntArray("block_poses", positions);
        }
    }

    public static Set<BlockPos> readBlockPoses(CompoundTag compoundTag) {
        Set<BlockPos> blockSet = new HashSet<>();
        if (!compoundTag.contains("block_poses")) {
            return blockSet;
        }

        int[] positions = compoundTag.getIntArray("block_poses");
        for (int pos = 0; pos < positions.length / 3; pos++) {
            blockSet.add(new BlockPos(positions[pos * 3], positions[pos * 3 + 1], positions[pos * 3 + 2]));
        }

        return blockSet;
    }

    private class Data implements ContainerData {

        @Override
        public int get(int index) {
            if (index == 0) {
                return harmoniousChangeTime;
            } else if (index == 1) {
                return currentState;
            } else {
                return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                harmoniousChangeTime = value;
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