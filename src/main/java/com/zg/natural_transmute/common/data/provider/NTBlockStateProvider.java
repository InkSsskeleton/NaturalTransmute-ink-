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
import net.minecraft.world.level.block.Blocks;
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
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_TUBE_CORAL_BLOCK.get(), Blocks.TUBE_CORAL_BLOCK);
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_BRAIN_CORAL_BLOCK.get(), Blocks.BRAIN_CORAL_BLOCK);
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_BUBBLE_CORAL_BLOCK.get(), Blocks.BUBBLE_CORAL_BLOCK);
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_FIRE_CORAL_BLOCK.get(), Blocks.FIRE_CORAL_BLOCK);
        this.registerCoralBlockStates(NTBlocks.ACTIVATED_HORN_CORAL_BLOCK.get(), Blocks.HORN_CORAL_BLOCK);
        this.registerCoralFanStates(NTBlocks.ACTIVATED_TUBE_CORAL_FAN.get(), Blocks.TUBE_CORAL_FAN, "coral_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_BRAIN_CORAL_FAN.get(), Blocks.BRAIN_CORAL_FAN, "coral_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_BUBBLE_CORAL_FAN.get(), Blocks.BUBBLE_CORAL_FAN, "coral_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_FIRE_CORAL_FAN.get(), Blocks.FIRE_CORAL_FAN, "coral_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_HORN_CORAL_FAN.get(), Blocks.HORN_CORAL_FAN, "coral_fan");
        this.registerCoralStates(NTBlocks.ACTIVATED_TUBE_CORAL.get(), Blocks.TUBE_CORAL);
        this.registerCoralStates(NTBlocks.ACTIVATED_BRAIN_CORAL.get(), Blocks.BRAIN_CORAL);
        this.registerCoralStates(NTBlocks.ACTIVATED_BUBBLE_CORAL.get(), Blocks.BUBBLE_CORAL);
        this.registerCoralStates(NTBlocks.ACTIVATED_FIRE_CORAL.get(), Blocks.FIRE_CORAL);
        this.registerCoralStates(NTBlocks.ACTIVATED_HORN_CORAL.get(), Blocks.HORN_CORAL);
        this.registerCoralFanStates(NTBlocks.ACTIVATED_TUBE_CORAL_WALL_FAN.get(), Blocks.TUBE_CORAL_FAN, "coral_wall_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_BRAIN_CORAL_WALL_FAN.get(), Blocks.BRAIN_CORAL_FAN, "coral_wall_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_BUBBLE_CORAL_WALL_FAN.get(), Blocks.BUBBLE_CORAL_FAN, "coral_wall_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_FIRE_CORAL_WALL_FAN.get(), Blocks.FIRE_CORAL_FAN, "coral_wall_fan");
        this.registerCoralFanStates(NTBlocks.ACTIVATED_HORN_CORAL_WALL_FAN.get(), Blocks.HORN_CORAL_FAN, "coral_wall_fan");
        this.logBlock((RotatedPillarBlock) NTBlocks.CORUNDUM.get());
        this.logBlock((RotatedPillarBlock) NTBlocks.AZURE_FROGLIGHT.get());
        this.registerCropStates(NTBlocks.BLUEBERRY_BUSH.get(), BlockStateProperties.AGE_3);
        this.registerPapyrusStates(NTBlocks.PAPYRUS.get());
        this.registerHarmoniousChangeStoveStates(NTBlocks.HARMONIOUS_CHANGE_STOVE.get());
    }

    private void simpleBlockWithRenderType(Block block, ResourceLocation type) {
        simpleBlock(block, models().cubeAll(this.name(block), this.blockTexture(block)).renderType(type));
    }

    private void registerCoralBlockStates(Block currentBlock, Block originalBlock) {
        ResourceLocation texture = this.mcLoc("block/" + this.name(originalBlock));
        ModelFile modelFile = this.models().cubeAll(this.name(currentBlock), texture);
        this.getVariantBuilder(currentBlock).partialState().modelForState().modelFile(modelFile).addModel();
    }

    private void registerCoralStates(Block currentBlock, Block originalBlock) {
        ResourceLocation texture = this.mcLoc("block/" + this.name(originalBlock));
        ModelFile modelFile = this.models().cross(this.name(currentBlock), texture).renderType(CUTOUT);
        this.getVariantBuilder(currentBlock).partialState().modelForState().modelFile(modelFile).addModel();
    }

    private void registerCoralFanStates(Block currentBlock, Block originalBlock, String parent) {
        ResourceLocation texture = this.mcLoc("block/" + this.name(originalBlock));
        ModelFile modelFile = this.models().singleTexture(this.name(currentBlock),
                this.mcLoc("block/" + parent), ("fan"), texture).renderType(CUTOUT);
        this.getVariantBuilder(currentBlock).partialState().modelForState().modelFile(modelFile).addModel();
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