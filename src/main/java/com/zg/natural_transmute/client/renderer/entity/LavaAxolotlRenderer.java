package com.zg.natural_transmute.client.renderer.entity;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.entities.animal.LavaAxolotl;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LavaAxolotlRenderer extends MobRenderer<LavaAxolotl, AxolotlModel<LavaAxolotl>> {

    public LavaAxolotlRenderer(EntityRendererProvider.Context context) {
        super(context, new AxolotlModel<>(context.bakeLayer(ModelLayers.AXOLOTL)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(LavaAxolotl pEntity) {
        return NaturalTransmute.prefix("textures/entity/lava_axolotl.png");
    }

}