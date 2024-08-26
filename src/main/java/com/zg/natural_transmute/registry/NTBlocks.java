package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.BlueberryBush;
import com.zg.natural_transmute.common.blocks.GatheringPlatform;
import com.zg.natural_transmute.common.blocks.Papyrus;
import com.zg.natural_transmute.utils.NTBlockRegUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class NTBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(NaturalTransmute.MOD_ID);
    public static final DeferredHolder<Block, Block> TURQUOISE = NTBlockRegUtils.normal("turquoise", ofFullCopy(Blocks.DEEPSLATE));
    public static final DeferredHolder<Block, Block> CORUNDUM = BLOCKS.register("corundum", () -> new RotatedPillarBlock(ofFullCopy(Blocks.DEEPSLATE)));
    public static final DeferredHolder<Block, Block> GATHERING_PLATFORM = BLOCKS.register("gathering_platform", GatheringPlatform::new);
    public static final DeferredHolder<Block, Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush", BlueberryBush::new);
    public static final DeferredHolder<Block, Block> PAPYRUS = BLOCKS.register("papyrus", Papyrus::new);

}