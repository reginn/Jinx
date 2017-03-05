package com.rgn.jinx;

import com.rgn.jinx.entity.JinxEntities;
import com.rgn.jinx.init.JinxCreativeTab;
import com.rgn.jinx.init.JinxItems;
import com.rgn.jinx.init.JinxMessages;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Jinx.MODID, version = Jinx.VERSION)
public class Jinx {
    public static final String MODID = "jinx";
    public static final String VERSION = "1.0";

    @Mod.Instance(Jinx.MODID)
    public static Jinx instance;

    public static CreativeTabs jinxTab = new JinxCreativeTab();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        JinxItems.init(event);
        JinxEntities.init(event);
        JinxMessages.init(event);
    }
}
