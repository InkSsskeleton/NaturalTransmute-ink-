package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static net.minecraft.data.worldgen.placement.OrePlacements.*;

public class NTPlacedFeatures {

    public static final ResourceKey<PlacedFeature> ORE_TURQUOISE_UPPER = createKey("ore_turquoise_upper");
    public static final ResourceKey<PlacedFeature> ORE_TURQUOISE_LOWER = createKey("ore_turquoise_lower");
    public static final ResourceKey<PlacedFeature> ORE_CORUNDUM_UPPER = createKey("ore_corundum_upper");
    public static final ResourceKey<PlacedFeature> ORE_CORUNDUM_LOWER = createKey("ore_corundum_lower");
    public static final ResourceKey<PlacedFeature> ORE_HETEROGENEOUS_STONE_UPPER = createKey("ore_heterogeneous_stone_upper");
    public static final ResourceKey<PlacedFeature> ORE_HETEROGENEOUS_STONE_LOWER = createKey("ore_heterogeneous_stone_lower");

    private static ResourceKey<PlacedFeature> createKey(String key) {
        return ResourceKey.create(Registries.PLACED_FEATURE, NaturalTransmute.prefix(key));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeature = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> turquoiseHolder = configuredFeature.getOrThrow(NTConfiguredFeatures.ORE_TURQUOISE);
        Holder<ConfiguredFeature<?, ?>> corundumHolder = configuredFeature.getOrThrow(NTConfiguredFeatures.ORE_CORUNDUM);
        Holder<ConfiguredFeature<?, ?>> hsHolder = configuredFeature.getOrThrow(NTConfiguredFeatures.ORE_HETEROGENEOUS_STONE);
        Holder<ConfiguredFeature<?, ?>> hsBuriedHolder = configuredFeature.getOrThrow(NTConfiguredFeatures.ORE_HETEROGENEOUS_STONE_BURIED);
        PlacementUtils.register(context, ORE_TURQUOISE_UPPER, turquoiseHolder, rareOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(128))));
        PlacementUtils.register(context, ORE_TURQUOISE_LOWER, turquoiseHolder, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(60))));
        PlacementUtils.register(context, ORE_CORUNDUM_UPPER, corundumHolder, rareOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(128))));
        PlacementUtils.register(context, ORE_CORUNDUM_LOWER, corundumHolder, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(60))));
        PlacementUtils.register(context, ORE_HETEROGENEOUS_STONE_UPPER, hsHolder, commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        PlacementUtils.register(context, ORE_HETEROGENEOUS_STONE_LOWER, hsBuriedHolder, commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(64))));
    }

}