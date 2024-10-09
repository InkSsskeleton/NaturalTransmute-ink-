package com.zg.natural_transmute.registry;

import com.mojang.serialization.MapCodec;
import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.level.feature.tree.foliage.EndAlsophilaFoliagePlacer;
import com.zg.natural_transmute.common.level.feature.tree.foliage.PlantainFoliagePlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTFoliagePlacerTypes {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(BuiltInRegistries.FOLIAGE_PLACER_TYPE, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<EndAlsophilaFoliagePlacer>> END_ALSOPHILA = register("end_alsophila", EndAlsophilaFoliagePlacer.CODEC);
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<PlantainFoliagePlacer>> PLANTAIN = register("plantain", PlantainFoliagePlacer.CODEC);

    private static <P extends FoliagePlacer> DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<P>> register(String name, MapCodec<P> codec) {
        return FOLIAGE_PLACER_TYPES.register(name + "_foliage_placer", () -> new FoliagePlacerType<>(codec));
    }

}