package com.rgn.jinx.item;

public enum EnumQuiverSize {

    SMALL(3),
    MIDDLE(6),
    LARGE(9);

    private int inventorySize;

    private EnumQuiverSize(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    public int getInventorySize() {
        return this.inventorySize;
    }

}
