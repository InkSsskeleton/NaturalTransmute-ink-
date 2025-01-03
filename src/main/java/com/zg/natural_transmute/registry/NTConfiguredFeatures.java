package com.zg.natural_transmute.registry;

import com.google.common.collect.ImmutableList;
import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.AlgalEndStone;
import com.zg.natural_transmute.common.level.feature.tree.foliage.EndAlsophilaFoliagePlacer;
import com.zg.natural_transmute.common.level.feature.tree.foliage.PlantainFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class NTConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PLANTAIN = createKey("plantain");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_ALSOPHILA = createKey("end_alsophila");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TURQUOISE = createKey("ore_turquoise");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_CORUNDUM = createKey("ore_corundum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_HETEROGENEOUS_STONE = createKey("ore_heterogeneous_stone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_HETEROGENEOUS_STONE_BURIED = createKey("ore_heterogeneous_stone_buried");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, NaturalTransmute.prefix(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest ruleTest = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        RuleTest ruleTest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruleTest2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        List<OreConfiguration.TargetBlockState> lapisOres = List.of(
                OreConfiguration.target(ruleTest1, NTBlocks.HETEROGENEOUS_STONE_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruleTest2, NTBlocks.DEEPSLATE_HETEROGENEOUS_STONE_ORE.get().defaultBlockState()));
        FeatureUtils.register(context, PLANTAIN, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NTBlocks.PLANTAIN_STEM.get()), new StraightTrunkPlacer((3), (2), (0)),
                BlockStateProvider.simple(NTBlocks.PLANTAIN_LEAVES.get()),
                new PlantainFoliagePlacer(ConstantInt.ZERO, ConstantInt.ZERO),
                new TwoLayersFeatureSize((1), (0), (2))).build()));
        FeatureUtils.register(context, END_ALSOPHILA, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NTBlocks.END_ALSOPHILA_LOG.get()), new StraightTrunkPlacer((6), (4), (0)),
                BlockStateProvider.simple(NTBlocks.END_ALSOPHILA_LEAVES.get().defaultBlockState().setValue(LeavesBlock.PERSISTENT, true)),
                new EndAlsophilaFoliagePlacer(ConstantInt.ZERO, ConstantInt.ZERO), new TwoLayersFeatureSize((1), (0), (2)))
                .decorators(ImmutableList.of(new AlterGroundDecorator(BlockStateProvider.simple(
                        NTBlocks.ALGAL_END_STONE.get().defaultBlockState().setValue(AlgalEndStone.WITHERED, Boolean.FALSE))))).build()));
        FeatureUtils.register(context, ORE_TURQUOISE, Feature.ORE, new OreConfiguration(ruleTest, NTBlocks.TURQUOISE.get().defaultBlockState(), 64));
        FeatureUtils.register(context, ORE_CORUNDUM, Feature.ORE, new OreConfiguration(ruleTest, NTBlocks.CORUNDUM.get().defaultBlockState(), 64));
        FeatureUtils.register(context, ORE_HETEROGENEOUS_STONE, Feature.ORE, new OreConfiguration(lapisOres, 7));
        FeatureUtils.register(context, ORE_HETEROGENEOUS_STONE_BURIED, Feature.ORE, new OreConfiguration(lapisOres, 7, (1.0F)));
    }

}