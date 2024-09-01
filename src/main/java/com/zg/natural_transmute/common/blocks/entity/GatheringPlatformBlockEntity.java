package com.zg.natural_transmute.common.blocks.entity;

import com.zg.natural_transmute.client.inventory.GatheringPlatformMenu;
import com.zg.natural_transmute.common.items.crafting.GatheringPlatformRecipe;
import com.zg.natural_transmute.common.items.crafting.GatheringPlatformRecipeInput;
import com.zg.natural_transmute.registry.NTBlockEntityTypes;
import com.zg.natural_transmute.registry.NTDataComponents;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.registry.NTRecipes;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GatheringPlatformBlockEntity extends SimpleContainerBlockEntity {

    private int gatheringTime;
    private int totalGatheringTime;
    private int currentState;
    private final ContainerData containerData = new Data();
    private final RecipeManager.CachedCheck<GatheringPlatformRecipeInput, ? extends GatheringPlatformRecipe> quickCheck;

    public GatheringPlatformBlockEntity(BlockPos pos, BlockState blockState) {
        super(NTBlockEntityTypes.GATHERING_PLATFORM.get(), pos, blockState);
        this.quickCheck = RecipeManager.createCheck(NTRecipes.GATHERING_PLATFORM_RECIPE.get());
        this.handler = new Handler(4);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, GatheringPlatformBlockEntity blockEntity) {
        ItemStack coreIngredient = blockEntity.handler.getStackInSlot(2);
        GatheringPlatformRecipe recipe = blockEntity.checkGatheringRecipe();
        if (recipe != null) {
            blockEntity.gathering(recipe, pos, state);
        } else {
            blockEntity.totalGatheringTime = 0;
            blockEntity.gatheringTime = 0;
            setChanged(level, pos, state);
        }

        if (coreIngredient.has(NTDataComponents.ASSOCIATED_BIOMES)) {
            blockEntity.currentState = 2;
            setChanged(level, pos, state);
        } else if (coreIngredient.is(NTItems.HETEROGENEOUS_STONE)) {
            blockEntity.currentState = 1;
            setChanged(level, pos, state);
        } else {
            blockEntity.currentState = 0;
            setChanged(level, pos, state);
        }
    }

    private void gathering(GatheringPlatformRecipe recipe, BlockPos pos, BlockState state) {
        if (this.level != null) {
            this.totalGatheringTime = this.gatheringTime;
            this.gatheringTime++;
            if (this.gatheringTime > recipe.gatheringTime) {
                this.handler.insertItem(3, recipe.assemble(this.getRecipeInput(), level.registryAccess()), false);
                NTCommonUtils.consumeIngredients(this);
                this.totalGatheringTime = 0;
                this.gatheringTime = 0;
            }

            setChanged(this.level, pos, state);
        }
    }

    @Nullable
    protected GatheringPlatformRecipe checkGatheringRecipe() {
        if (this.level != null) {
            RecipeHolder<? extends GatheringPlatformRecipe> holder = this.quickCheck.getRecipeFor(this.getRecipeInput(), this.level).orElse(null);
            return holder != null ? holder.value() : null;
        }

        return null;
    }

    private GatheringPlatformRecipeInput getRecipeInput() {
        ItemStack input1 = this.getItem(0);
        ItemStack input2 = this.getItem(1);
        ItemStack core = this.getItem(2);
        return new GatheringPlatformRecipeInput(input1, input2, core);
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.gatheringTime = tag.getInt("GatheringTime");
        this.totalGatheringTime = tag.getInt("TotalGatheringTime");
        this.currentState = tag.getInt("CurrentState");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("GatheringTime", this.gatheringTime);
        tag.putInt("TotalGatheringTime", this.totalGatheringTime);
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
                return totalGatheringTime;
            } else if (index == 2) {
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
                totalGatheringTime = value;
            } else if (index == 2) {
                currentState = value;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

}