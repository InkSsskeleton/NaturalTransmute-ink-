package com.zg.natural_transmute.common.items.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class HarmoniousChangeSerializer<T extends HarmoniousChangeRecipe> implements RecipeSerializer<T> {

    private final Factory<T> factory;
    private final MapCodec<T> codec;
    private final StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public HarmoniousChangeSerializer(Factory<T> factory) {
        this.factory = factory;
        this.codec = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("input1").forGetter(recipe -> recipe.input1),
                Ingredient.CODEC.fieldOf("input2").forGetter(recipe -> recipe.input2),
                Ingredient.CODEC.fieldOf("input3").forGetter(recipe -> recipe.input3),
                Ingredient.CODEC.fieldOf("fu_xiang").forGetter(recipe -> recipe.fuXiang),
                Ingredient.CODEC_NONEMPTY.fieldOf("results").forGetter(recipe -> recipe.results),
                Codec.INT.fieldOf("amount").forGetter(recipe -> recipe.amount),
                Codec.INT.fieldOf("time").forGetter(recipe -> recipe.time),
                Codec.BOOL.fieldOf("should_consume").orElse(true)
                        .forGetter(recipe -> recipe.shouldConsume)
        ).apply(instance, factory::create));
        this.streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        Ingredient input1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient input2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient input3 = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient fuXiang = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        Ingredient results = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
        return this.factory.create(input1, input2, input3, fuXiang, results,
                buffer.readVarInt(), buffer.readVarInt(), buffer.readBoolean());
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input1);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input2);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input3);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.fuXiang);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.results);
        buffer.writeVarInt(recipe.amount);
        buffer.writeVarInt(recipe.time);
        buffer.writeBoolean(recipe.shouldConsume);
    }

    @Override
    public MapCodec<T> codec() {
        return this.codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return this.streamCodec;
    }

    public interface Factory<T extends HarmoniousChangeRecipe> {
        T create(Ingredient input1, Ingredient input2, Ingredient input3, Ingredient fuXiang, Ingredient results, int amount, int time, boolean shouldConsume);
    }

}