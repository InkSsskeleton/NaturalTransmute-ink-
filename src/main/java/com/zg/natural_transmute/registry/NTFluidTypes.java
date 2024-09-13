package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class NTFluidTypes {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<FluidType, FluidType> MINE_WATER = FLUID_TYPES.register("mine_water",
            () -> new BaseFluidType(FluidType.Properties.create().supportsBoating(true),
                    ResourceLocation.withDefaultNamespace("block/water_still"),
                    ResourceLocation.withDefaultNamespace("block/water_flow")));

}