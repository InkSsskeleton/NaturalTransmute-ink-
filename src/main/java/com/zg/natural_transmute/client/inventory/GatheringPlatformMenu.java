package com.zg.natural_transmute.client.inventory;

import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTMenus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class GatheringPlatformMenu extends AbstractSimpleMenu {

    private final ContainerData containerData;

    @SuppressWarnings("unused")
    public GatheringPlatformMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public GatheringPlatformMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        this(containerId, inventory, access, new ItemStackHandler(4), new SimpleContainerData(2));
    }

    public GatheringPlatformMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler, ContainerData containerData) {
        super(NTMenus.GATHERING_PLATFORM.get(), containerId, inventory, access);
        checkContainerSize(inventory, 4);
        this.containerData = containerData;
        this.addSlot(new SlotItemHandler(itemHandler, 0, 31, 23));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 81, 5));
        this.addSlot(new SlotItemHandler(itemHandler, 2, 131, 23));
        this.addSlot(new NTResultSlot(itemHandler, 3, 81, 35));
        this.addDataSlots(containerData);
    }

    public int getGatheringTime() {
        return this.containerData.get(0);
    }

    public int getCurrentState() {
        return this.containerData.get(1);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, NTBlocks.GATHERING_PLATFORM.get());
    }

}