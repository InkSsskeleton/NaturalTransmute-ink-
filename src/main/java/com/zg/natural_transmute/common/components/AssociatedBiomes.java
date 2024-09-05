package com.zg.natural_transmute.common.components;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import java.util.List;

public record AssociatedBiomes(List<ResourceKey<Biome>> biomes) {

    public static final Codec<AssociatedBiomes> CODEC = ResourceKey.codec(Registries.BIOME)
            .listOf().xmap(AssociatedBiomes::new, AssociatedBiomes::biomes);
    public static final StreamCodec<ByteBuf, AssociatedBiomes> STREAM_CODEC =
            ResourceKey.streamCodec(Registries.BIOME).apply(ByteBufCodecs.list())
            .map(AssociatedBiomes::new, AssociatedBiomes::biomes);

}