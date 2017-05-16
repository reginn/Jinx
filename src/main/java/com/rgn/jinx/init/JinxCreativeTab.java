package com.rgn.jinx.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class JinxCreativeTab extends CreativeTabs {

    public JinxCreativeTab() {
        super(CreativeTabs.getNextID(), "Jinx");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(JinxItems.itemLeatherLongbow);
    }
}
