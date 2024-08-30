package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.AmberBlock;
import com.zg.natural_transmute.common.blocks.BlueberryBush;
import com.zg.natural_transmute.common.blocks.GatheringPlatform;
import com.zg.natural_transmute.common.blocks.stove.HarmoniousChangeStove;
import com.zg.natural_transmute.common.blocks.Papyrus;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.zg.natural_transmute.utils.NTBlockRegUtils.*;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class NTBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(NaturalTransmute.MOD_ID);
    public static final DeferredHolder<Block, Block> TURQUOISE = normal("turquoise", ofFullCopy(Blocks.DEEPSLATE));
    public static final DeferredHolder<Block, Block> CORUNDUM = BLOCKS.register("corundum",
            () -> new RotatedPillarBlock(ofFullCopy(Blocks.DEEPSLATE)));
    public static final DeferredHolder<Block, Block> AMBER_BLOCK = BLOCKS.register("amber_block", AmberBlock::new);
    public static final DeferredHolder<Block, Block> AZURE_FROGLIGHT = BLOCKS.register("azure_froglight",
            () -> new RotatedPillarBlock(ofFullCopy(Blocks.OCHRE_FROGLIGHT)));
    public static final DeferredHolder<Block, Block> CAVE_EARTH = normal("cave_earth", ofFullCopy(Blocks.DIRT));
    public static final DeferredHolder<Block, Block> DEATH_EARTH = normal("death_earth", ofFullCopy(Blocks.DIRT));
    public static final DeferredHolder<Block, Block> GRASSLAND_EARTH = normal("grassland_earth", ofFullCopy(Blocks.DIRT));
    public static final DeferredHolder<Block, Block> OCEAN_EARTH = normal("ocean_earth", ofFullCopy(Blocks.DIRT));
    public static final DeferredHolder<Block, Block> HETEROGENEOUS_STONE_ORE =
            ore("heterogeneous_stone_ore", UniformInt.of((2), (5)), ofFullCopy(Blocks.LAPIS_ORE));
    public static final DeferredHolder<Block, Block> DEEPSLATE_HETEROGENEOUS_STONE_ORE =
            ore("deepslate_heterogeneous_stone_ore", UniformInt.of((2), (5)), ofFullCopy(Blocks.DEEPSLATE_LAPIS_ORE));
    public static final DeferredHolder<Block, Block> GATHERING_PLATFORM = BLOCKS.register("gathering_platform", GatheringPlatform::new);
    public static final DeferredHolder<Block, Block> HARMONIOUS_CHANGE_STOVE = BLOCKS.register("harmonious_change_stove", HarmoniousChangeStove::new);
    public static final DeferredHolder<Block, Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush", BlueberryBush::new);
    public static final DeferredHolder<Block, Block> PAPYRUS = BLOCKS.register("papyrus", Papyrus::new);

}