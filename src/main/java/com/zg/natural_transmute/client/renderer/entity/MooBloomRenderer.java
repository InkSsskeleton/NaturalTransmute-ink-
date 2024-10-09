package com.zg.natural_transmute.client.renderer.entity;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.client.renderer.entity.layers.MooBloomMushroomLayer;
import com.zg.natural_transmute.common.entities.animal.MooBloom;
import com.zg.natural_transmute.registry.NTModelLayers;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MooBloomRenderer extends MobRenderer<MooBloom, CowModel<MooBloom>> {

    public MooBloomRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<>(context.bakeLayer(NTModelLayers.MOO_BLOOM)), 0.7F);
        this.addLayer(new MooBloomMushroomLayer(this, context.getBlockRenderDispatcher()));
    }

    @Override
    public ResourceLocation getTextureLocation(MooBloom entity) {
        return NaturalTransmute.prefix("textures/entity/moo_bloom.png");
    }

}