package com.rgn.jinx.init;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.item.ItemStatikkShiv;
import net.minecraft.item.Item;

public class JinxItems {

    public static Item itemStatikkShiv = (new ItemStatikkShiv())
            .setUnlocalizedName("itemStatikkShiv")
            .setCreativeTab(Jinx.jinxTab);
}
