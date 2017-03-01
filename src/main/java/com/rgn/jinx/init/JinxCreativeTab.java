package com.rgn.jinx.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class JinxCreativeTab extends CreativeTabs {

    public JinxCreativeTab() {
        super(CreativeTabs.getNextID(), "Jinx");
    }

    @Override
    public Item getTabIconItem() {
        return Items.ARROW;
    }
}
