package com.zg.natural_transmute.common.event;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.client.gui.GatheringPlatformScreen;
import com.zg.natural_transmute.client.gui.HarmoniousChangeStoveScreen;
import com.zg.natural_transmute.client.renderer.block.AmberBlockRenderer;
import com.zg.natural_transmute.client.renderer.block.GatheringPlatformRenderer;
import com.zg.natural_transmute.registry.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = NaturalTransmute.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusEventHandler {

    @SubscribeEvent
    public static void registerFlammable(FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void registerDispenserBehavior(FMLCommonSetupEvent event) {
        DispenserBlock.registerProjectileBehavior(NTItems.BREEZE_ARROW.get());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(NTMenus.GATHERING_PLATFORM.get(), GatheringPlatformScreen::new);
        event.register(NTMenus.HARMONIOUS_CHANGE_STOVE.get(), HarmoniousChangeStoveScreen::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(NTBlockEntityTypes.GATHERING_PLATFORM.get(), GatheringPlatformRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.AMBER_BLOCK.get(), AmberBlockRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(NTModelLayers.GATHERING_PLATFORM, GatheringPlatformRenderer::createBodyLayer);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerItemProperties(FMLClientSetupEvent event) {
        ItemProperties.register(NTItems.WHALE_BONE_BOW.get(), NaturalTransmute.prefix("pull"), ((stack, level, entity, seed) ->
                entity == null || entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F));
        ItemProperties.register(NTItems.WHALE_BONE_BOW.get(), NaturalTransmute.prefix("pulling"), ((stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F));
    }

}