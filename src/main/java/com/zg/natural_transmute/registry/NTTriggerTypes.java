package com.zg.natural_transmute.registry;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.data.advancements.critereon.HarmoniousChangeTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTTriggerTypes {

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, NaturalTransmute.MOD_ID);
    public static final DeferredHolder<CriterionTrigger<?>, HarmoniousChangeTrigger> HARMONIOUS_CHANGE = TRIGGER_TYPES.register("harmonious_change", HarmoniousChangeTrigger::new);

}