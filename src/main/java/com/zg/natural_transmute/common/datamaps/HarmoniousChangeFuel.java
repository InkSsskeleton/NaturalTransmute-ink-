package com.zg.natural_transmute.common.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public record HarmoniousChangeFuel(int amount) {

    public static final Codec<HarmoniousChangeFuel> AMOUNT_CODEC =
            ExtraCodecs.POSITIVE_INT.xmap(HarmoniousChangeFuel::new, HarmoniousChangeFuel::amount);
    public static final Codec<HarmoniousChangeFuel> CODEC = Codec.withAlternative(
            RecordCodecBuilder.create(in -> in.group(ExtraCodecs.POSITIVE_INT.fieldOf("amount")
                            .forGetter(HarmoniousChangeFuel::amount))
                    .apply(in, HarmoniousChangeFuel::new)), AMOUNT_CODEC);

}