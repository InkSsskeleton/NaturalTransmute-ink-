package com.zg.natural_transmute.client.inventory;

import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTDataComponents;
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

public class HarmoniousChangeStoveMenu extends AbstractSimpleMenu {

    private final ContainerData containerData;

    @SuppressWarnings("unused")
    public HarmoniousChangeStoveMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public HarmoniousChangeStoveMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        this(containerId, inventory, access, new ItemStackHandler(8), new SimpleContainerData(2));
    }

    public HarmoniousChangeStoveMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler, ContainerData containerData) {
        super(NTMenus.HARMONIOUS_CHANGE_STOVE.get(), containerId, inventory, access);
        checkContainerSize(inventory, 8);
        this.containerData = containerData;
        this.addSlot(new SlotItemHandler(itemHandler, 0, 8, 15));
        this.addSlot(new SlotItemHandler(itemHandler, 1, 26, 15));
        this.addSlot(new SlotItemHandler(itemHandler, 2, 44, 15));
        this.addSlot(new FuelSlot(itemHandler, 3, 29, 45));
        this.addSlot(new FuXiangSlot(itemHandler, 4, 68, 22));
        this.addSlot(new NTResultSlot(itemHandler, 5, 105, 41));
        this.addSlot(new NTResultSlot(itemHandler, 6, 123, 41));
        this.addSlot(new NTResultSlot(itemHandler, 7, 141, 41));
        this.addDataSlots(containerData);
    }

    public int getHarmoniousChangeTime() {
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
        return stillValid(this.access, player, NTBlocks.HARMONIOUS_CHANGE_STOVE.get());
    }

    private static class FuelSlot extends SlotItemHandler {

        public FuelSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return super.mayPlace(stack);
        }

    }

    private static class FuXiangSlot extends SlotItemHandler {

        public FuXiangSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.has(NTDataComponents.ASSOCIATED_BIOMES);
        }

    }

}