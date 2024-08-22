package com.zg.natural_transmute.registry;

import com.mojang.serialization.Codec;
import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.components.AssociatedBiomes;
import com.zg.natural_transmute.common.components.CatFoods;
import com.zg.natural_transmute.common.components.DogFoods;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<AssociatedBiomes>> ASSOCIATED_BIOMES = DATA_COMPONENT_TYPE.register("associated_biomes",
            () -> DataComponentType.<AssociatedBiomes>builder().persistent(AssociatedBiomes.CODEC).networkSynchronized(AssociatedBiomes.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CatFoods>> CAT_FOODS = DATA_COMPONENT_TYPE.register("cat_foods",
            () -> DataComponentType.<CatFoods>builder().persistent(CatFoods.CODEC).networkSynchronized(CatFoods.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DogFoods>> DOG_FOODS = DATA_COMPONENT_TYPE.register("dog_foods",
            () -> DataComponentType.<DogFoods>builder().persistent(DogFoods.CODEC).networkSynchronized(DogFoods.STREAM_CODEC).cacheEncoding().build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> SCULK_EQUIPMENT = DATA_COMPONENT_TYPE.register("sculk_equipment",
            () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).cacheEncoding().build());

}