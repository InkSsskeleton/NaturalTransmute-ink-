package com.zg.natural_transmute.common.data.models;

import com.google.gson.JsonElement;
import com.zg.natural_transmute.registry.NTBlocks;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NTBlockModelGenerators extends BlockModelGenerators {

    public NTBlockModelGenerators(
            Consumer<BlockStateGenerator> blockStateOutput,
            BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput,
            Consumer<Item> skippedAutoModelsOutput) {
        super(blockStateOutput, modelOutput, skippedAutoModelsOutput);
    }

    @Override
    public void run() {
        this.createMultiface(NTBlocks.SIMULATED_RAMBLER.get());
    }

}