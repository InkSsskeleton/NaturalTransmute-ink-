package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = NaturalTransmute.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NTFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<Fluid, FlowingFluid> MINE_WATER_STILL = FLUIDS.register("mine_water_still",
            () -> new BaseFlowingFluid.Source(NTFluids.MINE_WATER_PROPERTIES));
    public static final DeferredHolder<Fluid, FlowingFluid> MINE_WATER_FLOWING = FLUIDS.register("mine_water_flowing",
            () -> new BaseFlowingFluid.Flowing(NTFluids.MINE_WATER_PROPERTIES));

    private static final BaseFlowingFluid.Properties MINE_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            NTFluidTypes.MINE_WATER, MINE_WATER_STILL, MINE_WATER_FLOWING).block(NTBlocks.MINE_WATER).bucket(NTItems.MINE_WATER_BUCKET);

    @SubscribeEvent
    public static void registerInteraction(FMLCommonSetupEvent event) {
        List<Block> transformBlockList = new ArrayList<>();
        transformBlockList.add(Blocks.STONE);
        transformBlockList.add(Blocks.COBBLESTONE);
        transformBlockList.add(Blocks.CALCITE);
        transformBlockList.add(Blocks.ANDESITE);
        transformBlockList.add(Blocks.DIORITE);
        transformBlockList.add(Blocks.GRANITE);
        transformBlockList.add(Blocks.DEEPSLATE);
        transformBlockList.add(Blocks.BASALT);
        FluidInteractionRegistry.addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(NTFluidTypes.MINE_WATER.value(),
                fluidState -> fluidState.isSource() ? Blocks.OBSIDIAN.defaultBlockState() : Blocks.COBBLESTONE.defaultBlockState()));
        FluidInteractionRegistry.addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
                (level, currentPos, relativePos, currentState) -> level.getBlockState(currentPos.below()).is(NTBlocks.MINE_WATER),
                transformBlockList.get(RandomSource.create().nextInt(transformBlockList.size())).defaultBlockState()));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderLayer(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(MINE_WATER_STILL.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(MINE_WATER_FLOWING.get(), RenderType.translucent());
    }

}