package com.zg.natural_transmute.common.data.loot;

import com.google.common.collect.Iterables;
import com.zg.natural_transmute.common.blocks.BlueberryBush;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

public class NTBlockLoot extends VanillaBlockLoot {

    public NTBlockLoot(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        this.dropSelf(NTBlocks.TURQUOISE.get());
        this.dropSelf(NTBlocks.CORUNDUM.get());
        this.dropSelf(NTBlocks.GATHERING_PLATFORM.get());
        this.dropSelf(NTBlocks.PAPYRUS.get());
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
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Iterables.transform(NTBlocks.BLOCKS.getEntries(), DeferredHolder::get);
    }

}