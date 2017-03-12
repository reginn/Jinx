package com.rgn.jinx.item;

import com.rgn.jinx.Jinx;
import net.minecraft.util.ResourceLocation;

public enum EnumQuiverSize {

    SMALL (3, 1, 80, "textures/gui/container/quiver_small.png"),
    MIDDLE(3, 2, 71, "textures/gui/container/quiver_middle.png"),
    LARGE (3, 3, 62, "textures/gui/container/quiver_large.png");

    private int inventorySize;
    private int rowSize;
    private int colSize;
    private ResourceLocation resourceLocation;
    private int xStart;

    private EnumQuiverSize(int colSize, int rowSize, int xStart, String resourcePath) {
        this.inventorySize = colSize * rowSize;
        this.colSize = colSize;
        this.rowSize = rowSize;
        this.xStart = xStart;
        this.resourceLocation = new ResourceLocation(Jinx.MODID, resourcePath);
    }

    public int getInventorySize() {
        return this.inventorySize;
    }

    public int getRowSize() {
        return this.rowSize;
    }

    public int getColSize() {
        return this.colSize;
    }

    public int getXStart() {
        return this.xStart;
    }

    public ResourceLocation getGuiImageResourceLocation() {
        return this.resourceLocation;
    }

}
