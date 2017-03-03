package com.rgn.jinx.init;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.entity.projectile.EntityElvenArrow;
import com.rgn.jinx.item.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class JinxItems {

    public static Item itemLeatherLongbow = (new ItemElvenBow(EnumElvenBowType.LEATHER))
            .setUnlocalizedName("itemLeatherLongbow")
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemCompositeBow = (new ItemElvenBow(EnumElvenBowType.COMPOSITE))
            .setUnlocalizedName("itemCompositeBow")
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemElvenArrow = (new ItemElvenArrow(EnumElvenArrowType.ARROW))
            .setUnlocalizedName("itemElvenArrow")
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemTorchArrow = (new ItemTorchArrow(EnumElvenArrowType.TORCH))
            .setUnlocalizedName("itemTorchArrow")
            .setCreativeTab(Jinx.jinxTab);


    public static void init(FMLPreInitializationEvent event) {
        GameRegistry.register(JinxItems.itemLeatherLongbow, new ResourceLocation(Jinx.MODID, "leatherlongbow"));
        GameRegistry.register(JinxItems.itemCompositeBow,   new ResourceLocation(Jinx.MODID, "compositebow"));
        GameRegistry.register(JinxItems.itemElvenArrow, new ResourceLocation(Jinx.MODID,"arrow"));
        GameRegistry.register(JinxItems.itemTorchArrow, new ResourceLocation(Jinx.MODID,"torcharrow"));


        if (event.getSide().isClient()) {
            ModelLoader.setCustomModelResourceLocation(JinxItems.itemLeatherLongbow, 0, new ModelResourceLocation(JinxItems.itemLeatherLongbow.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(JinxItems.itemCompositeBow, 0, new ModelResourceLocation(JinxItems.itemCompositeBow.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(JinxItems.itemElvenArrow, 0, new ModelResourceLocation("arrow", "inventory"));
            ModelLoader.setCustomModelResourceLocation(JinxItems.itemTorchArrow, 0, new ModelResourceLocation("arrow", "inventory"));

        }
    }
}
