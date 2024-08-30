package com.zg.natural_transmute.client.renderer.entity;

import com.zg.natural_transmute.common.entities.projectile.BreezeArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.resources.ResourceLocation;

public class BreezeArrowRenderer extends ArrowRenderer<BreezeArrowEntity> {

    public BreezeArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BreezeArrowEntity entity) {
        return TippableArrowRenderer.NORMAL_ARROW_LOCATION;
    }

}