package com.zg.natural_transmute.common.data.models;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/** @noinspection deprecation*/
public class NTModelProvider implements DataProvider {

    private final PackOutput.PathProvider blockStatePathProvider;
    private final PackOutput.PathProvider modelPathProvider;

    public NTModelProvider(PackOutput output) {
        this.blockStatePathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.modelPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        Map<Block, BlockStateGenerator> map = Maps.newHashMap();
        Consumer<BlockStateGenerator> consumer = generator -> {
            Block block = generator.getBlock();
            BlockStateGenerator blockStateGenerator = map.put(block, generator);
            if (blockStateGenerator != null) {
                throw new IllegalStateException("Duplicate blockstate definition for " + block);
            }
        };
        Map<ResourceLocation, Supplier<JsonElement>> map1 = Maps.newHashMap();
        Set<Item> set = Sets.newHashSet();
        BiConsumer<ResourceLocation, Supplier<JsonElement>> biconsumer = (location, elementSupplier) -> {
            Supplier<JsonElement> supplier = map1.put(location, elementSupplier);
            if (supplier != null) {
                throw new IllegalStateException("Duplicate model definition for " + location);
            }
        };
        Consumer<Item> consumer1 = set::add;
        new NTBlockModelGenerators(consumer, biconsumer, consumer1).run();
        List<Block> list = BuiltInRegistries.BLOCK.entrySet().stream().map(Map.Entry::getValue)
                .filter(block -> !map.containsKey(block)).toList();
        if (list.isEmpty()) {
            BuiltInRegistries.BLOCK.forEach(block -> {
                Item item = Item.BY_BLOCK.get(block);
                if (item != null) {
                    if (set.contains(item)) {
                        return;
                    }

                    ResourceLocation modelLocation = ModelLocationUtils.getModelLocation(item);
                    if (!map1.containsKey(modelLocation)) {
                        map1.put(modelLocation, new DelegatedModel(ModelLocationUtils.getModelLocation(block)));
                    }
                }
            });
        }

        return CompletableFuture.allOf(
                this.saveCollection(output, block -> this.blockStatePathProvider
                        .json(block.builtInRegistryHolder().key().location()), map),
                this.saveCollection(output, this.modelPathProvider::json, map1));
    }

    private <T> CompletableFuture<?> saveCollection(
            CachedOutput output, Function<T, Path> resolveObjectPath,
            Map<T, ? extends Supplier<JsonElement>> objectToJsonMap) {
        return CompletableFuture.allOf(objectToJsonMap.entrySet().stream().map(entry -> {
            Path path = resolveObjectPath.apply(entry.getKey());
            JsonElement jsonElement = entry.getValue().get();
            return DataProvider.saveStable(output, jsonElement, path);
        }).toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Model Definitions";
    }

}