package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.Papyrus;
import com.zg.natural_transmute.registry.NTBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

@SuppressWarnings("SpellCheckingInspection")
public class NTBlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace(RenderType.cutout().name);
    private static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace(RenderType.cutoutMipped().name);
    private static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace(RenderType.translucent().name);

    public NTBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NaturalTransmute.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(NTBlocks.TURQUOISE.get());
        this.logBlock((RotatedPillarBlock) NTBlocks.CORUNDUM.get());
        this.registerCropStates(NTBlocks.BLUEBERRY_BUSH.get(), BlockStateProperties.AGE_3);
        this.registerPapyrusStates(NTBlocks.PAPYRUS.get());
    }

    private void registerCropStates(Block block, IntegerProperty property) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (int stage : property.getPossibleValues()) {
            String name = this.name(block) + "_stage" + stage;
            ResourceLocation texture = this.modLoc("block/" + name);
            ModelFile modelFile = this.models().crop(name, texture).renderType(CUTOUT);
            builder.partialState().with(property, stage).modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerPapyrusStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (Boolean value : Papyrus.TOP.getPossibleValues()) {
            String name = this.name(block) + (value ? "_top" : "_plant");
            ResourceLocation parent = this.mcLoc("block/tinted_cross");
            ResourceLocation texture = this.modLoc("block/" + name);
            ModelFile modelFile = this.models().singleTexture(name, parent, "texture", texture).renderType(CUTOUT);
            builder.partialState().with(Papyrus.TOP, value).modelForState().modelFile(modelFile).addModel();
        }
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}