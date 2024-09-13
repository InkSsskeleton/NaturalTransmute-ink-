package com.zg.natural_transmute.common.data.loot;

import com.google.common.collect.Iterables;
import com.zg.natural_transmute.common.blocks.BlueberryBush;
import com.zg.natural_transmute.common.blocks.HarmoniousChangeStove;
import com.zg.natural_transmute.common.blocks.state.properties.HCStovePart;
import com.zg.natural_transmute.common.data.NTBlockFamilies;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

public class NTBlockLoot extends VanillaBlockLoot {

    public NTBlockLoot(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {
        this.dropSelf(NTBlocks.PAPYRUS.get());
        this.dropSelf(NTBlocks.TURQUOISE.get());
        this.dropSelf(NTBlocks.CORUNDUM.get());
        this.dropSelf(NTBlocks.AMBER_BLOCK.get());
        this.dropSelf(NTBlocks.AZURE_FROGLIGHT.get());
        this.dropSelf(NTBlocks.GATHERING_PLATFORM.get());
        this.dropSelf(NTBlocks.BLUE_NETHER_BRICKS.get());
        this.dropSelf(NTBlocks.END_ALSOPHILA_LEAVES.get());
        this.dropSelf(NTBlocks.HARMONIOUS_CHANGE_STOVE.get());
        this.dropSelf(NTBlocks.END_ALSOPHILA_LOG.get());
        this.dropSelf(NTBlocks.END_ALSOPHILA_WOOD.get());
        this.dropSelf(NTBlocks.END_ALSOPHILA_PLANKS.get());
        this.dropSelf(NTBlocks.END_ALSOPHILA_SAPLING.get());
        this.dropSelf(NTBlocks.STRIPPED_END_ALSOPHILA_LOG.get());
        this.dropSelf(NTBlocks.STRIPPED_END_ALSOPHILA_WOOD.get());
        this.add(NTBlocks.END_ALSOPHILA_LEAVES.get(), block -> LootTable.lootTable());
        this.add(NTBlocks.ACTIVATED_TUBE_CORAL_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(Blocks.TUBE_CORAL_BLOCK, Blocks.DEAD_TUBE_CORAL_BLOCK));
        this.add(NTBlocks.ACTIVATED_BRAIN_CORAL_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(Blocks.BRAIN_CORAL_BLOCK, Blocks.DEAD_BRAIN_CORAL_BLOCK));
        this.add(NTBlocks.ACTIVATED_BUBBLE_CORAL_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(Blocks.BUBBLE_CORAL_BLOCK, Blocks.DEAD_BUBBLE_CORAL_BLOCK));
        this.add(NTBlocks.ACTIVATED_FIRE_CORAL_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(Blocks.FIRE_CORAL_BLOCK, Blocks.DEAD_FIRE_CORAL_BLOCK));
        this.add(NTBlocks.ACTIVATED_HORN_CORAL_BLOCK.get(), block -> this.createSingleItemTableWithSilkTouch(Blocks.HORN_CORAL_BLOCK, Blocks.DEAD_HORN_CORAL_BLOCK));
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_TUBE_CORAL_FAN.get(), Blocks.TUBE_CORAL_FAN);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_BRAIN_CORAL_FAN.get(), Blocks.BRAIN_CORAL_FAN);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_BUBBLE_CORAL_FAN.get(), Blocks.BUBBLE_CORAL_FAN);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_FIRE_CORAL_FAN.get(), Blocks.FIRE_CORAL_FAN);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_HORN_CORAL_FAN.get(), Blocks.HORN_CORAL_FAN);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_TUBE_CORAL.get(), Blocks.TUBE_CORAL);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_BRAIN_CORAL.get(), Blocks.BRAIN_CORAL);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_BUBBLE_CORAL.get(), Blocks.BUBBLE_CORAL);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_FIRE_CORAL.get(), Blocks.FIRE_CORAL);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_HORN_CORAL.get(), Blocks.HORN_CORAL);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_TUBE_CORAL_WALL_FAN.get(), Blocks.TUBE_CORAL_FAN);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_BRAIN_CORAL_WALL_FAN.get(), Blocks.BRAIN_CORAL_FAN);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_BUBBLE_CORAL_WALL_FAN.get(), Blocks.BUBBLE_CORAL_FAN);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_FIRE_CORAL_WALL_FAN.get(), Blocks.FIRE_CORAL_FAN);
        this.otherWhenSilkTouch(NTBlocks.ACTIVATED_HORN_CORAL_WALL_FAN.get(), Blocks.HORN_CORAL_FAN);
        this.dropOther(NTBlocks.HETEROGENEOUS_STONE_ORE.get(), NTItems.HETEROGENEOUS_STONE.get());
        this.dropOther(NTBlocks.DEEPSLATE_HETEROGENEOUS_STONE_ORE.get(), NTItems.HETEROGENEOUS_STONE.get());
        this.add(NTBlocks.HARMONIOUS_CHANGE_STOVE.get(), block -> LootTable.lootTable().withPool(
                this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(HarmoniousChangeStove.PART, HCStovePart.MAIN)
                                        .hasProperty(HarmoniousChangeStove.HALF, DoubleBlockHalf.LOWER)))))));
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        this.add(NTBlocks.ALGAL_END_STONE.get(), block -> this.createSingleItemTableWithSilkTouch(block, Blocks.END_STONE));
        this.add(NTBlocks.CAVE_EARTH.get(), block -> this.createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.add(NTBlocks.DEATH_EARTH.get(), block -> this.createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.add(NTBlocks.GRASSLAND_EARTH.get(), block -> this.createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.add(NTBlocks.OCEAN_EARTH.get(), block -> this.createSingleItemTableWithSilkTouch(block, Blocks.DIRT));
        this.add(NTBlocks.BLUEBERRY_BUSH.get(), block -> this.applyExplosionDecay(block, LootTable.lootTable()
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(NTBlocks.BLUEBERRY_BUSH.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(BlueberryBush.AGE, 3)))
                        .add(LootItem.lootTableItem(NTItems.BLUEBERRIES.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE))))
                .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(NTBlocks.BLUEBERRY_BUSH.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(BlueberryBush.AGE, 2)))
                        .add(LootItem.lootTableItem(NTItems.BLUEBERRIES.get()))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE))))));
        NTBlockFamilies.getAllFamilies().forEach(blockFamily -> {
            blockFamily.getVariants().forEach((variant, block) -> this.dropSelf(block));
            this.dropSelf(blockFamily.getBaseBlock());
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Iterables.transform(NTBlocks.BLOCKS.getEntries(), DeferredHolder::get);
    }

}