package com.rgn.jinx.init;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.event.ForgeEvents;
import com.rgn.jinx.item.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.model.animation.Clips;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sun.nio.cs.ext.JIS_X_0201;

public class JinxItems {

    public static Item itemLeatherLongbow = (new ItemElvenBow(EnumElvenBowType.LEATHER))
            .setUnlocalizedName("itemLeatherLongbow")
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemCompositeBow = (new ItemElvenBow(EnumElvenBowType.COMPOSITE))
            .setUnlocalizedName("itemCompositeBow")
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemElvenBow = (new ItemElvenBow(EnumElvenBowType.ELVEN))
            .setUnlocalizedName("itemElvenBow")
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemTorchArrow = (new ItemTorchArrow(EnumElvenArrowType.TORCH))
            .setUnlocalizedName("itemTorchArrow")
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemMiddleQuiver = (new ItemQuiver(EnumQuiverSize.MIDDLE))
            .setUnlocalizedName("itemMiddleQuiver")
            .setCreativeTab(Jinx.jinxTab);


    public static void init(FMLPreInitializationEvent event) {
        GameRegistry.register(JinxItems.itemLeatherLongbow, new ResourceLocation(Jinx.MODID, "leatherlongbow"));
        GameRegistry.register(JinxItems.itemCompositeBow, new ResourceLocation(Jinx.MODID, "compositebow"));
        GameRegistry.register(JinxItems.itemElvenBow, new ResourceLocation(Jinx.MODID, "elvenbow"));
        GameRegistry.register(JinxItems.itemTorchArrow, new ResourceLocation(Jinx.MODID, "torcharrow"));
        GameRegistry.register(JinxItems.itemMiddleQuiver, new ResourceLocation(Jinx.MODID, "middlequiver"));

        if (event.getSide().isClient()) {
            ModelLoader.setCustomModelResourceLocation(JinxItems.itemLeatherLongbow, 0, new ModelResourceLocation(JinxItems.itemLeatherLongbow.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(JinxItems.itemCompositeBow, 0, new ModelResourceLocation(JinxItems.itemCompositeBow.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(JinxItems.itemElvenBow, 0, new ModelResourceLocation(JinxItems.itemElvenBow.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(JinxItems.itemTorchArrow, 0, new ModelResourceLocation(JinxItems.itemTorchArrow.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(JinxItems.itemMiddleQuiver, 0, new ModelResourceLocation(JinxItems.itemMiddleQuiver.getRegistryName(), "inventory"));
        }


        MinecraftForge.EVENT_BUS.register(new ForgeEvents());

    }
}
