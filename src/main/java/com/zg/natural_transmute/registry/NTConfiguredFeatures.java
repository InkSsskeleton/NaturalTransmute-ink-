package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class NTConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TURQUOISE = createKey("ore_turquoise");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CORUNDUM = createKey("ore_corundum");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturalTransmute.prefix(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest ruleTest = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        FeatureUtils.register(context, ORE_TURQUOISE, Feature.ORE, new OreConfiguration(ruleTest, NTBlocks.TURQUOISE.get().defaultBlockState(), 64));
        FeatureUtils.register(context, ORE_CORUNDUM, Feature.ORE, new OreConfiguration(ruleTest, NTBlocks.CORUNDUM.get().defaultBlockState(), 64));
    }

}