package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NORMAL = CREATIVE_MODE_TABS.register("normal", () -> CreativeModeTab
            .builder().title(Component.translatable("itemGroup." + NaturalTransmute.MOD_ID)).icon(() -> new ItemStack(NTItems.GATHERING_PLATFORM))
            .displayItems((parameters, output) -> NTItems.ITEMS.getEntries().forEach(holder -> output.accept(holder.get()))).build());

}