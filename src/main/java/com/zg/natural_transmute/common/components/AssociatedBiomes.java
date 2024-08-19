package com.zg.natural_transmute.common.components;

import com.mojang.serialization.Codec;
import com.zg.natural_transmute.NaturalTransmute;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.biome.Biome;

import java.util.List;
import java.util.function.Consumer;

public record AssociatedBiomes(List<ResourceKey<Biome>> biomes) implements TooltipProvider {

    public static final Codec<AssociatedBiomes> CODEC = ResourceKey.codec(Registries.BIOME)
            .listOf().xmap(AssociatedBiomes::new, AssociatedBiomes::biomes);
    public static final StreamCodec<ByteBuf, AssociatedBiomes> STREAM_CODEC = ResourceKey.streamCodec(Registries.BIOME)
            .apply(ByteBufCodecs.list()).map(AssociatedBiomes::new, AssociatedBiomes::biomes);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag tooltipFlag) {
        String key = "tooltip." + NaturalTransmute.MOD_ID + ".associated_biomes";
        Component prefixComponent = Component.translatable(key).withStyle(ChatFormatting.AQUA);
        Component biomesComponent = Component.empty();
        for (ResourceKey<Biome> biome : this.biomes) {
            String languageKey = biome.location().toLanguageKey("biome");
            biomesComponent = biomesComponent.copy().append(Component.translatable(languageKey)).append(" ");
        }

        tooltipAdder.accept(prefixComponent.copy().append(biomesComponent.copy().withStyle(ChatFormatting.GREEN)));
    }

}