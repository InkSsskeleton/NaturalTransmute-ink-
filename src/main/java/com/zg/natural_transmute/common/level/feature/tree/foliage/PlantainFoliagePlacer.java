package com.zg.natural_transmute.common.level.feature.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zg.natural_transmute.common.blocks.Plantain;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTFoliagePlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Arrays;

public class PlantainFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<PlantainFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((placer) -> foliagePlacerParts(placer).apply(placer, PlantainFoliagePlacer::new));

    public PlantainFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return NTFoliagePlacerTypes.PLANTAIN.get();
    }

    private void placeLeavesRowWithoutOffset(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos) {
        this.placeLeavesRow(level, foliageSetter, random, config, pos, 0, 0, false);
    }

    @Override
    protected void createFoliage(
            LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config,
            int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        this.placeLeavesRowWithoutOffset(level, foliageSetter, random, config, attachment.pos());
        Arrays.stream(Direction.BY_2D_DATA).toList().forEach(direction -> {
            BlockPos pos = attachment.pos().relative(direction);
            BlockState state = NTBlocks.PLANTAIN.get().defaultBlockState();
            Direction randomDirection = Direction.from2DDataValue(random.nextInt(4));
            this.placeLeavesRowWithoutOffset(level, foliageSetter, random, config, pos);
            foliageSetter.set(pos.below(), state.setValue(Plantain.FACING, randomDirection));
        });
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 2;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return false;
    }

}