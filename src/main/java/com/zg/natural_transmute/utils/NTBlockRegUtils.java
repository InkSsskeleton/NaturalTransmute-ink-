package com.zg.natural_transmute.utils;

import com.zg.natural_transmute.common.blocks.modified.AxeStrippableBlock;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class NTBlockRegUtils {

    public static DeferredHolder<Block, Block> register(String name, Supplier<Block> block) {
        DeferredHolder<Block, Block> register = NTBlocks.BLOCKS.register(name, block);
        NTItems.ITEMS.register(name, () -> new BlockItem(register.get(), new Item.Properties()));
        return register;
    }

    public static DeferredHolder<Block, Block> normal(String name, BlockBehaviour.Properties properties) {
        return register(name, () -> new Block(properties));
    }

    public static DeferredHolder<Block, Block> ore(String name, IntProvider xpRange, BlockBehaviour.Properties properties) {
        return register(name, () -> new DropExperienceBlock(xpRange, properties.requiresCorrectToolForDrops()));
    }

    public static DeferredHolder<Block, Block> wood(String name, Supplier<Block> block, MapColor mapColor, float strength) {
        return register(name, () -> new AxeStrippableBlock(block, BlockBehaviour.Properties
                .of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS)
                .strength(strength).sound(SoundType.WOOD).ignitedByLava()));
    }

    public static DeferredHolder<Block, Block> wood(String name, MapColor mapColor, float strength) {
        return register(name, () -> new RotatedPillarBlock(BlockBehaviour.Properties
                .of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS)
                .strength(strength).sound(SoundType.WOOD).ignitedByLava()));
    }

}