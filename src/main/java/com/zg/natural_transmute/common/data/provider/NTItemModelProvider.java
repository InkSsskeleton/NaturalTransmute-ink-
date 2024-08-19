package com.zg.natural_transmute.common.data.provider;

import com.zg.natural_transmute.NaturalTransmute;
import com.zg.natural_transmute.utils.NTCommonUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NTItemModelProvider extends ItemModelProvider {

    public NTItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NaturalTransmute.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        NTCommonUtils.getKnownItems().forEach(this::simpleItem);
    }

    private void simpleItem(Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        this.withExistingParent(path, this.mcLoc("item/generated"))
                .texture("layer0", this.modLoc("item/" + path));
    }

}