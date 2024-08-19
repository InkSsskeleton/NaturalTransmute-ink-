package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.components.AssociatedBiomes;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AssociatedBiomes>> ASSOCIATED_BIOMES = DATA_COMPONENT_TYPE.register("associated_biomes",
            () -> DataComponentType.<AssociatedBiomes>builder().persistent(AssociatedBiomes.CODEC).networkSynchronized(AssociatedBiomes.STREAM_CODEC).cacheEncoding().build());

}