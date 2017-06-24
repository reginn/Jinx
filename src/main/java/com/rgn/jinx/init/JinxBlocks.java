package com.rgn.jinx.init;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.block.BlockLamp;
import com.rgn.jinx.block.BlockSteadyLadder;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class JinxBlocks {

    public static ResourceLocation steadyLadderResourceLocation = new ResourceLocation(Jinx.MODID, "steadyladder");
    public static ResourceLocation lampResourceLocation = new ResourceLocation(Jinx.MODID, "lamp");

    public static Block blockSteadyLadder = (new BlockSteadyLadder())
            .setUnlocalizedName("blocksteadyladder")
            .setRegistryName(steadyLadderResourceLocation)
            .setHardness(2.0F)
            .setCreativeTab(Jinx.jinxTab);

    public static Block blockLamp = (new BlockLamp())
            .setUnlocalizedName("blocklamp")
            .setRegistryName(lampResourceLocation)
            .setCreativeTab(Jinx.jinxTab);

    public static ItemBlock itemBlockSteadyLadder = new ItemBlock(blockSteadyLadder);
    public static ItemBlock itemBlockLamp = new ItemBlock(blockLamp);



    @SubscribeEvent
    public void register(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        registry.register(blockSteadyLadder);
        registry.register(blockLamp);
    }

    @SubscribeEvent
    public void registerItemBlock(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        itemBlockSteadyLadder.setRegistryName(steadyLadderResourceLocation);
        itemBlockLamp.setRegistryName(lampResourceLocation);

        registry.register(itemBlockSteadyLadder);
        registry.register(itemBlockLamp);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void register(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(itemBlockSteadyLadder, 0, new ModelResourceLocation(steadyLadderResourceLocation, "inventory"));
        ModelLoader.setCustomModelResourceLocation(itemBlockLamp, 0, new ModelResourceLocation(lampResourceLocation, "inventory"));
    }
}
