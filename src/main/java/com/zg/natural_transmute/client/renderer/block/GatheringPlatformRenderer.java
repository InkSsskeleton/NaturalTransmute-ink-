package com.zg.natural_transmute.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.entity.GatheringPlatformBlockEntity;
import com.zg.natural_transmute.registry.NTModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GatheringPlatformRenderer implements BlockEntityRenderer<GatheringPlatformBlockEntity> {

    private static final Material TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS,
            NaturalTransmute.prefix("textures/entity/gathering_platform.png"));
    private final ModelPart bone1;
    private final ModelPart bone2;

    public GatheringPlatformRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelPart = context.bakeLayer(NTModelLayers.GATHERING_PLATFORM);
        ModelPart group = modelPart.getChild("group");
        this.bone1 = group.getChild("bone1");
        this.bone2 = group.getChild("bone2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition group = partDefinition.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 0.0F));
        group.addOrReplaceChild("bone1", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(-1.0F, 0.0F, 0.0F));
        group.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 8)
                .addBox(0.0F, -19.0F, -2.0F, 2.0F, 4.0F, 4.0F, CubeDeformation.NONE),
                PartPose.offset(0.0F, 17.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void render(GatheringPlatformBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        poseStack.translate(0.0F, -0.9F, 0.0F);
        VertexConsumer consumer = TEXTURE.buffer(bufferSource, RenderType::entitySolid);
        this.bone1.render(poseStack, consumer, packedLight, packedOverlay);
        this.bone2.render(poseStack, consumer, packedLight, packedOverlay);
        poseStack.popPose();
    }

}