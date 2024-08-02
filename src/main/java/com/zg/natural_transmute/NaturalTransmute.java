package com.zg.natural_transmute;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import java.util.Locale;

@Mod(NaturalTransmute.MOD_ID)
public class NaturalTransmute {

    public static final String MOD_ID = "natural_transmute";

    public NaturalTransmute(IEventBus modEventBus, ModContainer modContainer) {

    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

}