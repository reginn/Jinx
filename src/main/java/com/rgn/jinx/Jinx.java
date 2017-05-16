package com.rgn.jinx;

import com.rgn.jinx.entity.JinxEntities;
import com.rgn.jinx.init.*;
import com.rgn.jinx.world.gen.JinxWorldGenerator;
import com.rgn.jinx.world.gen.WorldGenNetherDungeons;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
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
        JinxItems.init(event);
        JinxBlocks.init(event);
        JinxEntities.init(event);
        JinxMessages.init(event);
        JinxRecipeRegistry.register();
        LootTableList.register(customLootTable);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new JinxWorldGenerator(), 1);

    }
}
