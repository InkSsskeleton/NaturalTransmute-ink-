package com.zg.natural_transmute.common.data;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.data.provider.NTItemModelProvider;
import com.zg.natural_transmute.common.data.provider.NTLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = NaturalTransmute.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NTDataGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        generator.addProvider(event.includeServer(), new NTItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new NTLanguageProvider(output, "en_us"));
        generator.addProvider(event.includeServer(), new NTLanguageProvider(output, "zh_cn"));
    }

}