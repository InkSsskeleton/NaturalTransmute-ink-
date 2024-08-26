package com.zg.natural_transmute.registry;

import com.mojang.serialization.Codec;
import com.zg.natural_transmute.NaturalTransmute;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class NTAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, NaturalTransmute.MOD_ID);

    private static DeferredHolder<AttachmentType<?>, AttachmentType<Float>> registerFloat(String name) {
        return ATTACHMENT_TYPES.register(name, () -> AttachmentType.builder(() -> 0.0F).serialize(Codec.FLOAT).build());
    }

}