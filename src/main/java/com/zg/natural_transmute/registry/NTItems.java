package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

import static com.zg.natural_transmute.utils.NTItemRegUtils.fx;

public class NTItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(NaturalTransmute.MOD_ID);
    public static final DeferredHolder<Item, Item> H_BADLANDS = fx("h_badlands", List.of(Biomes.BADLANDS));
    public static final DeferredHolder<Item, Item> H_BASALT_DELTAS = fx("h_basalt_deltas", List.of(Biomes.BASALT_DELTAS));
    public static final DeferredHolder<Item, Item> H_BEACH = fx("h_beach", List.of(Biomes.BEACH));
    public static final DeferredHolder<Item, Item> H_BIRCH_FOREST = fx("h_birch_forest",
            List.of(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST));
    public static final DeferredHolder<Item, Item> H_CHERRY_GROVE = fx("h_cherry_grove", List.of(Biomes.CHERRY_GROVE));
    public static final DeferredHolder<Item, Item> H_CRIMSON_FOREST = fx("h_crimson_forest", List.of(Biomes.CRIMSON_FOREST));
    public static final DeferredHolder<Item, Item> H_DARK_FOREST = fx("h_dark_forest", List.of(Biomes.DARK_FOREST));
    public static final DeferredHolder<Item, Item> H_DEEPSLATE = fx("h_deepslate", List.of());
    public static final DeferredHolder<Item, Item> H_DEEP_DARK = fx("h_deep_dark", List.of(Biomes.DEEP_DARK));
    public static final DeferredHolder<Item, Item> H_DESERT = fx("h_desert", List.of(Biomes.DESERT));
    public static final DeferredHolder<Item, Item> H_DRIPSTONE_CAVES = fx("h_dripstone_caves", List.of(Biomes.DRIPSTONE_CAVES));
    public static final DeferredHolder<Item, Item> H_END = fx("h_end", List.of(Biomes.THE_END));
    public static final DeferredHolder<Item, Item> H_END_HIGHLANDS = fx("h_end_highlands",
            List.of(Biomes.SMALL_END_ISLANDS, Biomes.END_BARRENS, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS));
    public static final DeferredHolder<Item, Item> H_FLOWER_FOREST = fx("h_flower_forest", List.of(Biomes.FLOWER_FOREST));
    public static final DeferredHolder<Item, Item> H_FOREST = fx("h_forest", List.of(Biomes.FOREST));
    public static final DeferredHolder<Item, Item> H_FROZEN_OCEAN = fx("h_frozen_ocean",
            List.of(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN));
    public static final DeferredHolder<Item, Item> H_GIANT_TREE_TAIGA = fx("h_giant_tree_taiga",
            List.of(Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA));
    public static final DeferredHolder<Item, Item> H_ICE_SPIKES = fx("h_ice_spikes", List.of(Biomes.ICE_SPIKES));
    public static final DeferredHolder<Item, Item> H_JUNGLE = fx("h_jungle",
            List.of(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE));
    public static final DeferredHolder<Item, Item> H_LUSH_CAVE = fx("h_lush_cave", List.of(Biomes.LUSH_CAVES));
    public static final DeferredHolder<Item, Item> H_MANGROVE_SWAMP = fx("h_mangrove_swamp", List.of(Biomes.MANGROVE_SWAMP));
    public static final DeferredHolder<Item, Item> H_MEADOW = fx("h_meadow", List.of(Biomes.MEADOW));
    public static final DeferredHolder<Item, Item> H_MOUNTAINS = fx("h_mountains", List.of(Biomes.STONY_PEAKS));
    public static final DeferredHolder<Item, Item> H_MUSHROOM = fx("h_mushroom", List.of(Biomes.MUSHROOM_FIELDS));
    public static final DeferredHolder<Item, Item> H_NETHER = fx("h_nether", List.of(Biomes.NETHER_WASTES));
    public static final DeferredHolder<Item, Item> H_OCEAN = fx("h_ocean",
            List.of(Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN,
                    Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN));
    public static final DeferredHolder<Item, Item> H_PLAINS = fx("h_plains",
            List.of(Biomes.PLAINS, Biomes.SUNFLOWER_PLAINS));
    public static final DeferredHolder<Item, Item> H_RIVER = fx("h_river",
            List.of(Biomes.RIVER, Biomes.FROZEN_RIVER));
    public static final DeferredHolder<Item, Item> H_SAVANNA = fx("h_savanna",
            List.of(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU));
    public static final DeferredHolder<Item, Item> H_SNOWY_SLOPES = fx("h_snowy_slopes",
            List.of(Biomes.SNOWY_SLOPES, Biomes.JAGGED_PEAKS, Biomes.FROZEN_PEAKS));
    public static final DeferredHolder<Item, Item> H_SNOWY_TUNDRA = fx("h_snowy_tundra", List.of(Biomes.SNOWY_PLAINS));
    public static final DeferredHolder<Item, Item> H_SOUL_SAND_VALLEY = fx("h_soul_sand_valley", List.of(Biomes.SOUL_SAND_VALLEY));
    public static final DeferredHolder<Item, Item> H_STONE_SHORE = fx("h_stone_shore", List.of(Biomes.STONY_SHORE));
    public static final DeferredHolder<Item, Item> H_SWAMP = fx("h_swamp", List.of(Biomes.SWAMP));
    public static final DeferredHolder<Item, Item> H_TAIGA = fx("h_taiga",
            List.of(Biomes.TAIGA, Biomes.SNOWY_TAIGA));
    public static final DeferredHolder<Item, Item> H_WARM_OCEAN = fx("h_warm_ocean", List.of(Biomes.WARM_OCEAN));
    public static final DeferredHolder<Item, Item> H_WARPED_FOREST = fx("h_warped_forest", List.of(Biomes.WARPED_FOREST));
    public static final DeferredHolder<Item, Item> H_WIND = fx("h_wind",
            List.of(Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_SAVANNA, Biomes.WINDSWEPT_GRAVELLY_HILLS));
    public static final DeferredHolder<Item, Item> H_WOODED_BADLANDS = fx("h_wooded_badlands", List.of(Biomes.WOODED_BADLANDS));

}