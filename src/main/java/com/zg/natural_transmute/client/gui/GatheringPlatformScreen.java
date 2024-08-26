package com.zg.natural_transmute.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.client.inventory.GatheringPlatformMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GatheringPlatformScreen extends AbstractContainerScreen<GatheringPlatformMenu> {

    private static final String PATH = "textures/gui/gathering_platform_gui_";

    public GatheringPlatformScreen(GatheringPlatformMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        PoseStack poseStack = guiGraphics.pose();
        ResourceLocation texture = NaturalTransmute.prefix(PATH + this.menu.getCurrentState() + ".png");
        guiGraphics.blit(texture, i, j, 0, 0, this.imageWidth, this.imageHeight);
        poseStack.pushPose();
    }

}