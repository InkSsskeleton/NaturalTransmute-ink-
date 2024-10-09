package com.zg.natural_transmute.common.level.feature.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.zg.natural_transmute.registry.NTFoliagePlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Arrays;
import java.util.List;

public class EndAlsophilaFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<EndAlsophilaFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((placer) -> foliagePlacerParts(placer).apply(placer, EndAlsophilaFoliagePlacer::new));

    public EndAlsophilaFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return NTFoliagePlacerTypes.END_ALSOPHILA.get();
    }

    private void placeLeavesRowWithoutOffset(LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config, BlockPos pos) {
        this.placeLeavesRow(level, foliageSetter, random, config, pos, 0, 0, false);
    }

    private boolean isCorrectPos(BlockPos pos1, BlockPos pos2) {
        int d1 = Mth.abs(pos1.getX() - pos2.getX());
        int d2 = Mth.abs(pos1.getZ() - pos2.getZ());
        return (d1 == 3 && d2 == 0) || (d2 == 3 && d1 == 0) || (d1 == 2 && d2 == 2);
    }

    @Override
    protected void createFoliage(
            LevelSimulatedReader level, FoliageSetter foliageSetter, RandomSource random, TreeConfiguration config,
            int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos tempPos = attachment.pos().above(2);
        List<Direction> directions = Arrays.stream(Direction.BY_2D_DATA).toList();
        directions.forEach(direction -> this.placeLeavesRowWithoutOffset(level, foliageSetter, random, config, attachment.pos().relative(direction)));
        directions.forEach(direction -> this.placeLeavesRowWithoutOffset(level, foliageSetter, random, config, attachment.pos().above().relative(direction)));
        directions.forEach(direction -> this.placeLeavesRowWithoutOffset(level, foliageSetter, random, config, tempPos.relative(direction, 2)));
        directions.forEach(direction -> this.placeLeavesRowWithoutOffset(level, foliageSetter, random, config, tempPos.relative(direction).relative(direction.getClockWise())));
        BlockPos.withinManhattanStream(tempPos, 3, 0, 3).filter(pos -> this.isCorrectPos(tempPos, pos))
                .forEach(pos -> this.placeLeavesRowWithoutOffset(level, foliageSetter, random, config, pos));
        BlockPos.withinManhattanStream(tempPos.above(), 3, 0, 3).filter(pos -> this.isCorrectPos(tempPos.above(), pos))
                .forEach(pos -> this.placeLeavesRowWithoutOffset(level, foliageSetter, random, config, pos));
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 4;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return false;
    }

}