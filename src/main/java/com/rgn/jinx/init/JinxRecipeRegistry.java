package com.rgn.jinx.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class JinxRecipeRegistry {

    public static void register() {
        registerBowRecipe();
    }

    public static void registerBowRecipe() {
        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(JinxItems.itemLeatherLongbow, 1),
                        new Object[]
                                {
                                        "LBL",
                                        Character.valueOf('L'), "leather",
                                        Character.valueOf('B'), Items.BOW
                                }));


        GameRegistry.addRecipe(
                new ShapedOreRecipe(
                        new ItemStack(JinxItems.itemCompositeBow, 1),
                        new Object[]
                                {
                                        " L ",
                                        "IBI",
                                        " L ",
                                        Character.valueOf('I'), "ingotIron",
                                        Character.valueOf('L'), "leather",
                                        Character.valueOf('B'), Items.BOW
                                }));


    }
}
