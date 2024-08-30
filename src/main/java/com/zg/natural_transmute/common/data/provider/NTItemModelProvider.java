package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.registry.NTBlocks;
import com.zg.natural_transmute.registry.NTItems;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NTItemModelProvider extends ItemModelProvider {

    public NTItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NaturalTransmute.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.simpleItem(NTItems.BLUEBERRIES.get());
        this.simpleItem(NTItems.PAPYRUS.get());
        this.simpleBlockItem(NTBlocks.TURQUOISE.get());
        this.simpleBlockItem(NTBlocks.CORUNDUM.get());
        this.simpleBlockItem(NTBlocks.AMBER_BLOCK.get());
        this.simpleBlockItem(NTBlocks.AZURE_FROGLIGHT.get());
        this.simpleBlockItem(NTBlocks.CAVE_EARTH.get());
        this.simpleBlockItem(NTBlocks.DEATH_EARTH.get());
        this.simpleBlockItem(NTBlocks.GRASSLAND_EARTH.get());
        this.simpleBlockItem(NTBlocks.OCEAN_EARTH.get());
        this.simpleBlockItem(NTBlocks.HETEROGENEOUS_STONE_ORE.get());
        this.simpleBlockItem(NTBlocks.DEEPSLATE_HETEROGENEOUS_STONE_ORE.get());
        for (Item item : NTCommonUtils.getKnownItems()) {
            ResourceLocation key = BuiltInRegistries.ITEM.getKey(item);
            if (item instanceof TieredItem) {
                this.withExistingParent(key.getPath(), this.mcLoc("item/handheld"))
                        .texture("layer0", this.modLoc("item/" + key.getPath()));
            } else if (item instanceof BowItem) {
                this.bowItem(item);
            } else if (!(item instanceof BlockItem)) {
                this.simpleItem(item);
            }
        }
    }

    private void simpleItem(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        this.withExistingParent(path, this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + path));
    }

    private void simpleBlockItem(Block block) {
        this.withExistingParent(this.name(block), this.modLoc("block/" + this.name(block)));
    }

    private void bowItem(Item item) {
        String name = BuiltInRegistries.ITEM.getKey(item).getPath();
        ModelFile.UncheckedModelFile bowModel = new ModelFile.UncheckedModelFile(this.modLoc("item/" + name));
        this.withExistingParent(name, this.modLoc("item/nt_bow"))
                .texture("layer0", this.modLoc("item/" + name))
                .override().predicate(this.modLoc("pulling"), 1)
                .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + name + "_pulling_0"))).end()
                .override().predicate(this.modLoc("pulling"), 1).predicate(this.modLoc("pull"), 0.65F)
                .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + name + "_pulling_1"))).end()
                .override().predicate(this.modLoc("pulling"), 1).predicate(this.modLoc("pull"), 0.9F)
                .model(new ModelFile.UncheckedModelFile(this.modLoc("item/" + name + "_pulling_2"))).end();
        for (int i = 0; i < 3; i++) {
            String path = name + "_pulling_" + i;
            this.getBuilder(path).parent(bowModel).texture("layer0", this.modLoc("item/" + path));
        }
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

}