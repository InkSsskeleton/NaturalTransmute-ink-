package com.zg.natural_transmute.common.data.tags;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NTItemTags {

    public static final TagKey<Item> GRASS = create("grass");
    public static final TagKey<Item> FU_XIANG = create("fu_xiang");
    public static final TagKey<Item> COOKED_MEAT = create("cooked_meat");
    public static final TagKey<Item> COOKED_FISH = create("cooked_fish");
    public static final TagKey<Item> END_ALSOPHILA_LOGS = create("end_alsophila_logs");
    public static final TagKey<Item> END_ALSOPHILA_SAPLING_PLACEABLE = create("end_alsophila_sapling_placeable");

    private static TagKey<Item> create(String name) {
        return ItemTags.create(NaturalTransmute.prefix(name));
    }

}