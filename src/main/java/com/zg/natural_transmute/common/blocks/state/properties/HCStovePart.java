package com.zg.natural_transmute.common.blocks.state.properties;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;

@MethodsReturnNonnullByDefault
public enum HCStovePart implements StringRepresentable {

    MAIN(1, "main"),
    MAIN_HEAD(2, "main_head"),
    RIGHT(3, "right"),
    RIGHT_HEAD(4, "right_head");

    private final int id;
    private final String name;

    HCStovePart(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

}