package com.zg.natural_transmute.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zg.natural_transmute.common.blocks.entity.AmberBlockBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AmberBlockRenderer implements BlockEntityRenderer<AmberBlockBlockEntity> {

    private final ItemRenderer itemRenderer;

    public AmberBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(AmberBlockBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        ItemStack stack = blockEntity.getItem(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 1.0F, 0.5F);
            this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight,
                    packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }

        poseStack.popPose();
    }

}