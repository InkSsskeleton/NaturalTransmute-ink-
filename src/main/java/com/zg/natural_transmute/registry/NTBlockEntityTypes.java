package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.entity.GatheringPlatformBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("ConstantConditions")
public class NTBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GatheringPlatformBlockEntity>> GATHERING_PLATFORM = BLOCK_ENTITY_TYPES.register("gathering_platform",
            () -> BlockEntityType.Builder.of(GatheringPlatformBlockEntity::new, NTBlocks.GATHERING_PLATFORM.get()).build(null));

}