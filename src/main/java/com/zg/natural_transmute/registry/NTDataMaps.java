package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.datamaps.HarmoniousChangeFuel;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class NTDataMaps {

    public static final DataMapType<Item, HarmoniousChangeFuel> HARMONIOUS_CHANGE_FUELS = DataMapType.builder(
            NaturalTransmute.prefix("harmonious_change"), Registries.ITEM, HarmoniousChangeFuel.CODEC)
            .synced(HarmoniousChangeFuel.AMOUNT_CODEC, false).build();

}