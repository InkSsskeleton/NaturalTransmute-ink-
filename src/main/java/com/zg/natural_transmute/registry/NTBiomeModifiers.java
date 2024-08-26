package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class NTBiomeModifiers {

    public static final ResourceKey<BiomeModifier> NT_ORE = createKey("nt_ore");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, NaturalTransmute.prefix(name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biome = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);
        HolderSet.Named<Biome> overworldBiomes = biome.getOrThrow(BiomeTags.IS_OVERWORLD);
        context.register(NT_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                overworldBiomes, HolderSet.direct(
                        placedFeature.getOrThrow(NTPlacedFeatures.ORE_TURQUOISE_UPPER),
                        placedFeature.getOrThrow(NTPlacedFeatures.ORE_TURQUOISE_LOWER),
                        placedFeature.getOrThrow(NTPlacedFeatures.ORE_CORUNDUM_UPPER),
                        placedFeature.getOrThrow(NTPlacedFeatures.ORE_CORUNDUM_LOWER)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }

}