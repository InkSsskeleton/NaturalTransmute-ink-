package com.zg.natural_transmute.registry;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class NTTreeGrowers {

    public static final TreeGrower PLANTAIN = new TreeGrower("plantain",
            Optional.empty(), Optional.of(NTConfiguredFeatures.PLANTAIN), Optional.empty());
    public static final TreeGrower END_ALSOPHILA = new TreeGrower("end_alsophila",
            Optional.empty(), Optional.of(NTConfiguredFeatures.END_ALSOPHILA), Optional.empty());

}