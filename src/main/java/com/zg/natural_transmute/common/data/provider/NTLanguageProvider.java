package com.zg.natural_transmute.common.data.provider;

import com.google.gson.JsonObject;
import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class NTLanguageProvider extends LanguageProvider {

    private final Map<String, String> enData = new TreeMap<>();
    private final Map<String, String> cnData = new TreeMap<>();
    private final PackOutput output;
    private final String locale;

    public NTLanguageProvider(PackOutput output, String locale) {
        super(output, NaturalTransmute.MOD_ID, locale);
        this.output = output;
        this.locale = locale;
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup." + NaturalTransmute.MOD_ID, "Natural Transmute", "自然通路");
        this.addItem(NTItems.H_BADLANDS, "Withered", "枯槁缚相");
        this.addItem(NTItems.H_BASALT_DELTAS, "Melting", "熔裂缚相");
        this.addItem(NTItems.H_BEACH, "Sea Bore", "涌潮缚相");
        this.addItem(NTItems.H_BIRCH_FOREST, "Green Leaf", "苍叶缚相");
        this.addItem(NTItems.H_CHERRY_GROVE, "", "");
        this.addItem(NTItems.H_CRIMSON_FOREST, "Red Fungus", "赤菌缚相");
        this.addItem(NTItems.H_DARK_FOREST, "Deep Forest", "深林缚相");
        this.addItem(NTItems.H_DEEPSLATE, "Deep Slate", "深岩缚相");
        this.addItem(NTItems.H_DEEP_DARK, "Echo", "回音缚相");
        this.addItem(NTItems.H_DESERT, "Hot-Dry", "燥热缚相");
        this.addItem(NTItems.H_DRIPSTONE_CAVES, "Years", "岁月缚相");
        this.addItem(NTItems.H_END, "The End", "终末缚相");
        this.addItem(NTItems.H_END_HIGHLANDS, "Dragon Fight", "龙诀缚相");
        this.addItem(NTItems.H_FLOWER_FOREST, "Colorful", "缤纷缚相");
        this.addItem(NTItems.H_FOREST, "Greenery", "树海缚相");
        this.addItem(NTItems.H_FROZEN_OCEAN, "Green with Cool", "冷翠缚相");
        this.addItem(NTItems.H_GIANT_TREE_TAIGA, "Giant", "巨人缚相");
        this.addItem(NTItems.H_ICE_SPIKES, "Extremely Cold", "极寒缚相");
        this.addItem(NTItems.H_JUNGLE, "Rainwood", "雨木缚相");
        this.addItem(NTItems.H_LUSH_CAVE, "Secret Garden", "秘园缚相");
        this.addItem(NTItems.H_MANGROVE_SWAMP, "Frogs Croaking", "蛙鸣缚相");
        this.addItem(NTItems.H_MEADOW, "Harmony", "和声缚相");
        this.addItem(NTItems.H_MOUNTAINS, "Mountains Blessing", "峦祈缚相");
        this.addItem(NTItems.H_MUSHROOM, "Fungus Stories", "菌语缚相");
        this.addItem(NTItems.H_NETHER, "Nether", "地狱缚相");
        this.addItem(NTItems.H_OCEAN, "Vast", "浩瀚缚相");
        this.addItem(NTItems.H_PLAINS, "", "厚载缚相");
        this.addItem(NTItems.H_RIVER, "Rushing", "奔流缚相");
        this.addItem(NTItems.H_SAVANNA, "Wilderness", "荒野缚相");
        this.addItem(NTItems.H_SNOWY_SLOPES, "Piercingly Cold", "凛峰缚相");
        this.addItem(NTItems.H_SNOWY_TUNDRA, "Holiness", "霜洁缚相");
        this.addItem(NTItems.H_SOUL_SAND_VALLEY, "Soul Lock", "锁魂缚相");
        this.addItem(NTItems.H_STONE_SHORE, "", "沧碣缚相");
        this.addItem(NTItems.H_SWAMP, "Silt Pool", "淤潭缚相");
        this.addItem(NTItems.H_TAIGA, "Winter Forest", "冬林缚相");
        this.addItem(NTItems.H_WARM_OCEAN, "Gorgeous", "斑斓缚相");
        this.addItem(NTItems.H_WARPED_FOREST, "", "影染缚相");
        this.addItem(NTItems.H_WIND, "Wind", "浩风缚相");
        this.addItem(NTItems.H_WOODED_BADLANDS, "Nature Wakes", "复苏缚相");
        this.addTooltips("associated_biomes", "Associated Biomes: ", "关联的生物群系：");
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput cache) {
        this.addTranslations();
        Path path = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(NaturalTransmute.MOD_ID).resolve("lang");
        if (this.locale.equals("en_us") && !this.enData.isEmpty()) {
            return this.save(this.enData, cache, path.resolve("en_us.json"));
        }

        if (this.locale.equals("zh_cn") && !this.cnData.isEmpty()) {
            return this.save(this.cnData, cache, path.resolve("zh_cn.json"));
        }

        return CompletableFuture.allOf();
    }

    private CompletableFuture<?> save(Map<String, String> data, CachedOutput cache, Path target) {
        JsonObject json = new JsonObject();
        data.forEach(json::addProperty);
        return DataProvider.saveStable(cache, json, target);
    }

    private void addBlock(Supplier<? extends Block> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void addItem(Supplier<? extends Item> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void addTooltips(String key, String en, String cn) {
        this.add("tooltip." + NaturalTransmute.MOD_ID + "." + key, en, cn);
    }

    private void add(String key, String en, String cn) {
        if (this.locale.equals("en_us") && !this.enData.containsKey(key)) {
            this.enData.put(key, en);
        } else if (this.locale.equals("zh_cn") && !this.cnData.containsKey(key)) {
            this.cnData.put(key, cn);
        }
    }

}