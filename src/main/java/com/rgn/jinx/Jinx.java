package com.rgn.jinx;

import com.rgn.jinx.init.JinxCreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Jinx.MODID, version = Jinx.VERSION)
public class Jinx
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";

    public static CreativeTabs jinxTab = new JinxCreativeTab();
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
    }
}
