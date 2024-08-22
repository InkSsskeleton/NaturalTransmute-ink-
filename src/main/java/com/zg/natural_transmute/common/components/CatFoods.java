package com.zg.natural_transmute.common.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.CatVariant;
import org.jetbrains.annotations.Nullable;

public record CatFoods(@Nullable ResourceKey<CatVariant> variant) {

    public static final Codec<CatFoods> CODEC = ResourceKey.codec(Registries.CAT_VARIANT).xmap(CatFoods::new, CatFoods::variant);
    public static final StreamCodec<ByteBuf, CatFoods> STREAM_CODEC = ResourceKey.streamCodec(Registries.CAT_VARIANT).map(CatFoods::new, CatFoods::variant);

}