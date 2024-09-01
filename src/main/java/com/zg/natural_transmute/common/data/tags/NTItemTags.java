package com.zg.natural_transmute.common.data.tags;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class NTItemTags {

    public static final TagKey<Item> FU_XIANG = create("fu_xiang");

    private static TagKey<Item> create(String name) {
        return ItemTags.create(NaturalTransmute.prefix(name));
    }

}