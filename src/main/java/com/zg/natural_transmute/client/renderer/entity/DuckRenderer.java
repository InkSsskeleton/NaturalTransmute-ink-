package com.zg.natural_transmute.client.renderer.entity;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.client.model.entity.DuckModel;
import com.zg.natural_transmute.common.entities.animal.Duck;
import com.zg.natural_transmute.registry.NTModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DuckRenderer extends MobRenderer<Duck, DuckModel<Duck>> {

    public DuckRenderer(EntityRendererProvider.Context context) {
        super(context, new DuckModel<>(context.bakeLayer(NTModelLayers.DUCK)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(Duck entity) {
        return NaturalTransmute.prefix("textures/entity/duck.png");
    }

    @Override
    protected float getBob(Duck livingBase, float partialTicks) {
        float f = Mth.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
        float f1 = Mth.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * f1;
    }

}