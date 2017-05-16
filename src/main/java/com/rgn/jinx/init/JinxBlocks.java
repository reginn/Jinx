package com.rgn.jinx.init;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.block.BlockLamp;
import com.rgn.jinx.block.BlockSteadyLadder;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class JinxBlocks {

    public static Block blockSteadyLadder = (new BlockSteadyLadder())
            .setUnlocalizedName("blocksteadyladder")
            .setHardness(2.0F)
            .setCreativeTab(Jinx.jinxTab);

    public static Block blockLamp = (new BlockLamp())
            .setUnlocalizedName("blocklamp")
            .setCreativeTab(Jinx.jinxTab);

    public static ItemBlock itemBlockSteadyLadder = new ItemBlock(blockSteadyLadder);
    public static ItemBlock itemBlockLamp = new ItemBlock(blockLamp);

    public static ResourceLocation steadyLadderResourceLocation = new ResourceLocation(Jinx.MODID, "steadyladder");
    public static ResourceLocation lampResourceLocation = new ResourceLocation(Jinx.MODID, "lamp");

    public static void init(FMLPreInitializationEvent event) {
        GameRegistry.register(blockSteadyLadder, steadyLadderResourceLocation);
        GameRegistry.register(itemBlockSteadyLadder, steadyLadderResourceLocation);

        GameRegistry.register(blockLamp, lampResourceLocation);
        GameRegistry.register(itemBlockLamp, lampResourceLocation);

        if (event.getSide() == Side.CLIENT) {
            ModelLoader.setCustomModelResourceLocation(itemBlockSteadyLadder, 0, new ModelResourceLocation(steadyLadderResourceLocation, "inventory"));
            ModelLoader.setCustomModelResourceLocation(itemBlockLamp, 0, new ModelResourceLocation(lampResourceLocation, "inventory"));
        }


    }
}
