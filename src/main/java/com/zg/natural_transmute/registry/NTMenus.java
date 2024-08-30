package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.client.inventory.GatheringPlatformMenu;
import com.zg.natural_transmute.client.inventory.HarmoniousChangeStoveMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<GatheringPlatformMenu>> GATHERING_PLATFORM = register(GatheringPlatformMenu::new, "gathering_platform");
    public static final DeferredHolder<MenuType<?>, MenuType<HarmoniousChangeStoveMenu>> HARMONIOUS_CHANGE_STOVE = register(HarmoniousChangeStoveMenu::new, "harmonious_change_stove");

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(IContainerFactory<T> factory, String name) {
        return MENUS.register(name + "_menu", () -> new MenuType<>(factory, FeatureFlags.DEFAULT_FLAGS));
    }

}