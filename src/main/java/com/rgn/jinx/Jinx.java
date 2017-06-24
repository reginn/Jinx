package com.rgn.jinx;

import com.rgn.jinx.client.event.JinxClientEvents;
import com.rgn.jinx.event.JinxServerEvents;
import com.rgn.jinx.init.*;
import com.rgn.jinx.world.gen.JinxWorldGenerator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Jinx.MODID, version = Jinx.VERSION)
public class Jinx {
    public static final String MODID = "jinx";
    public static final String VERSION = "1.0";

    @Mod.Instance(Jinx.MODID)
    public static Jinx instance;

    public static CreativeTabs jinxTab = new JinxCreativeTab();

    public static final ResourceLocation customLootTable = new ResourceLocation(MODID, "custom_dungeon");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // NOP
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new JinxWorldGenerator(), 1);
        JinxMessages.init(event);

        LootTableList.register(customLootTable);

    }

    @EventHandler
    public void construct(FMLConstructionEvent event) {

        MinecraftForge.EVENT_BUS.register(new JinxItems());
        MinecraftForge.EVENT_BUS.register(new JinxBlocks());
        MinecraftForge.EVENT_BUS.register(new JinxEntities());
        MinecraftForge.EVENT_BUS.register(new JinxClientEvents());
        MinecraftForge.EVENT_BUS.register(new JinxServerEvents());
        MinecraftForge.EVENT_BUS.register(new JinxRecipes());
    }


}
