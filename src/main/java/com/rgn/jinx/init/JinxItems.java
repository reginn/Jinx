package com.rgn.jinx.init;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.item.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class JinxItems {

    public static Item itemLeatherLongbow = (new ItemElvenBow(EnumElvenBowType.LEATHER))
            .setUnlocalizedName("itemLeatherLongbow")
            .setRegistryName(new ResourceLocation(Jinx.MODID, "leatherlongbow"))
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemCompositeBow = (new ItemElvenBow(EnumElvenBowType.COMPOSITE))
            .setUnlocalizedName("itemCompositeBow")
            .setRegistryName(new ResourceLocation(Jinx.MODID, "compositebow"))
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemElvenBow = (new ItemElvenBow(EnumElvenBowType.ELVEN))
            .setUnlocalizedName("itemElvenBow")
            .setRegistryName(new ResourceLocation(Jinx.MODID, "elvenbow"))
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemTorchArrow = (new ItemTorchArrow(EnumElvenArrowType.TORCH))
            .setUnlocalizedName("itemTorchArrow")
            .setRegistryName(new ResourceLocation(Jinx.MODID, "torcharrow"))
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemMiddleQuiver = (new ItemQuiver(EnumQuiverSize.MIDDLE))
            .setUnlocalizedName("itemMiddleQuiver")
            .setRegistryName(new ResourceLocation(Jinx.MODID, "middlequiver"))
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemSmallQuiver = (new ItemQuiver(EnumQuiverSize.SMALL))
            .setUnlocalizedName("itemSmallQuiver")
            .setRegistryName(new ResourceLocation(Jinx.MODID, "smallquiver"))
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemLargeQuiver = (new ItemQuiver(EnumQuiverSize.LARGE))
            .setUnlocalizedName("itemLargeQuiver")
            .setRegistryName(new ResourceLocation(Jinx.MODID, "largequiver"))
            .setCreativeTab(Jinx.jinxTab);

    public static Item itemSeedBag = (new ItemSeedBag())
            .setUnlocalizedName("itemSeedBag")
            .setRegistryName(new ResourceLocation(Jinx.MODID, "seedbag"))
            .setCreativeTab(Jinx.jinxTab);


    @SubscribeEvent
    public void register(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(JinxItems.itemLeatherLongbow);
        registry.register(JinxItems.itemCompositeBow);
        registry.register(JinxItems.itemElvenBow);
        registry.register(JinxItems.itemSmallQuiver);
        registry.register(JinxItems.itemMiddleQuiver);
        registry.register(JinxItems.itemLargeQuiver);
        registry.register(JinxItems.itemSeedBag);
        registry.register(JinxItems.itemTorchArrow);

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void register(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(JinxItems.itemLeatherLongbow, 0, new ModelResourceLocation(JinxItems.itemLeatherLongbow.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(JinxItems.itemCompositeBow, 0, new ModelResourceLocation(JinxItems.itemCompositeBow.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(JinxItems.itemElvenBow, 0, new ModelResourceLocation(JinxItems.itemElvenBow.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(JinxItems.itemTorchArrow, 0, new ModelResourceLocation(JinxItems.itemTorchArrow.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(JinxItems.itemMiddleQuiver, 0, new ModelResourceLocation(JinxItems.itemMiddleQuiver.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(JinxItems.itemSmallQuiver, 0, new ModelResourceLocation(JinxItems.itemSmallQuiver.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(JinxItems.itemLargeQuiver, 0, new ModelResourceLocation(JinxItems.itemLargeQuiver.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(JinxItems.itemSeedBag, 0, new ModelResourceLocation(JinxItems.itemSeedBag.getRegistryName(), "inventory"));
    }
}
