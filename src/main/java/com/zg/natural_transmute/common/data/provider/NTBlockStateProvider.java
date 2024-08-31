package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.common.blocks.state.properties.HCStovePart;
import com.zg.natural_transmute.common.blocks.HarmoniousChangeStove;
import com.zg.natural_transmute.common.blocks.Papyrus;
import com.zg.natural_transmute.registry.NTBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Map;

@SuppressWarnings("SpellCheckingInspection")
public class NTBlockStateProvider extends BlockStateProvider {

    private static final ResourceLocation CUTOUT = ResourceLocation.withDefaultNamespace(RenderType.cutout().name);
    private static final ResourceLocation CUTOUT_MIPPED = ResourceLocation.withDefaultNamespace(RenderType.cutoutMipped().name);
    private static final ResourceLocation TRANSLUCENT = ResourceLocation.withDefaultNamespace(RenderType.translucent().name);
    private static final Map<Direction, Integer> DIRECTION_WITH_ROTATION =
            Map.of(Direction.NORTH, 0, Direction.EAST, 90,
                    Direction.SOUTH, 180, Direction.WEST, 270);

    public NTBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NaturalTransmute.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlock(NTBlocks.TURQUOISE.get());
        this.simpleBlock(NTBlocks.CAVE_EARTH.get());
        this.simpleBlock(NTBlocks.DEATH_EARTH.get());
        this.simpleBlock(NTBlocks.GRASSLAND_EARTH.get());
        this.simpleBlock(NTBlocks.OCEAN_EARTH.get());
        this.simpleBlock(NTBlocks.HETEROGENEOUS_STONE_ORE.get());
        this.simpleBlock(NTBlocks.DEEPSLATE_HETEROGENEOUS_STONE_ORE.get());
        this.simpleBlockWithRenderType(NTBlocks.AMBER_BLOCK.get(), TRANSLUCENT);
        this.logBlock((RotatedPillarBlock) NTBlocks.CORUNDUM.get());
        this.logBlock((RotatedPillarBlock) NTBlocks.AZURE_FROGLIGHT.get());
        this.registerCropStates(NTBlocks.BLUEBERRY_BUSH.get(), BlockStateProperties.AGE_3);
        this.registerPapyrusStates(NTBlocks.PAPYRUS.get());
        this.registerHarmoniousChangeStoveStates(NTBlocks.HARMONIOUS_CHANGE_STOVE.get());
    }

    private void simpleBlockWithRenderType(Block block, ResourceLocation type) {
        simpleBlock(block, models().cubeAll(this.name(block), this.blockTexture(block)).renderType(type));
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
            ModelFile modelFile = this.models().singleTexture(name, parent, "cross", texture).renderType(CUTOUT);
            builder.partialState().with(Papyrus.TOP, value).modelForState().modelFile(modelFile).addModel();
        }
    }

    private void registerHarmoniousChangeStoveStates(Block block) {
        VariantBlockStateBuilder builder = this.getVariantBuilder(block);
        for (HCStovePart part : HarmoniousChangeStove.PART.getPossibleValues()) {
            for (DoubleBlockHalf half : HarmoniousChangeStove.HALF.getPossibleValues()) {
                for (Direction direction : HarmoniousChangeStove.FACING.getPossibleValues()) {
                    for (Boolean lit : HarmoniousChangeStove.LIT.getPossibleValues()) {
                        String name = "harmonious_change_stove_" + (lit ? "on" : "off");
                        ResourceLocation parent = this.modLoc("block/harmonious_change_stove");
                        ResourceLocation texture = this.modLoc("block/" + name);
                        ModelFile modelFile = this.models().withExistingParent(name, parent)
                                .texture("0", texture).texture("particle", texture).renderType(CUTOUT);
                        builder.partialState().with(HarmoniousChangeStove.PART, part)
                                .with(HarmoniousChangeStove.HALF, half)
                                .with(HarmoniousChangeStove.FACING, direction)
                                .with(HarmoniousChangeStove.LIT, lit).modelForState()
                                .rotationY(DIRECTION_WITH_ROTATION.get(direction))
                                .modelFile(modelFile).addModel();
                    }
                }
            }
        }
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}