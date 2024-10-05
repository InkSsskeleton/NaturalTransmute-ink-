package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.effects.NTMobEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<MobEffect, MobEffect> VULNERABLE = MOB_EFFECTS.register("vulnerable", () -> new NTMobEffect(MobEffectCategory.HARMFUL, 0xe07c4d));

}