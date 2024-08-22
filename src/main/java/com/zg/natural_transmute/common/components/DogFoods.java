package com.zg.natural_transmute.common.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.WolfVariant;

public record DogFoods(ResourceKey<WolfVariant> variant) {

    public static final Codec<DogFoods> CODEC = ResourceKey.codec(Registries.WOLF_VARIANT).xmap(DogFoods::new, DogFoods::variant);
    public static final StreamCodec<ByteBuf, DogFoods> STREAM_CODEC = ResourceKey.streamCodec(Registries.WOLF_VARIANT).map(DogFoods::new, DogFoods::variant);

}