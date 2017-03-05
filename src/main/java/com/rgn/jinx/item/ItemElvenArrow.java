package com.rgn.jinx.item;

import net.minecraft.item.ItemArrow;

public abstract class ItemElvenArrow extends ItemArrow {

    protected EnumElvenArrowType arrowType;

    public ItemElvenArrow(EnumElvenArrowType _arrowType) {
        super();

        this.arrowType = _arrowType;
    }
}
