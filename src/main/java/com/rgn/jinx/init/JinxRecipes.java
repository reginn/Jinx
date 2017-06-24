package com.rgn.jinx.init;

import com.rgn.jinx.Jinx;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

public class JinxRecipes {

    private final static ResourceLocation JINX = new ResourceLocation(Jinx.MODID);

    private IRecipe leatherLongbowRecipe = new ShapedOreRecipe(
            JinxItems.itemLeatherLongbow.getRegistryName(),
            new ItemStack(JinxItems.itemLeatherLongbow, 1),
            new Object[]
                    {
                            "LBL",
                            Character.valueOf('L'), "leather",
                            Character.valueOf('B'), Items.BOW
                    })
            .setRegistryName(new ResourceLocation(Jinx.MODID, "leatherlongbow_recipe"));

    private IRecipe compositeBowRecipe = new ShapedOreRecipe(
            JinxItems.itemCompositeBow.getRegistryName(),
            new ItemStack(JinxItems.itemCompositeBow, 1),
            new Object[]
                    {
                            " L ",
                            "IBI",
                            " L ",
                            Character.valueOf('I'), "ingotIron",
                            Character.valueOf('L'), "leather",
                            Character.valueOf('B'), Items.BOW
                    })
            .setRegistryName(new ResourceLocation(Jinx.MODID, "compositebow_recipe"));


    private IRecipe torchArrowRecipe = new ShapelessOreRecipe(
            JinxItems.itemTorchArrow.getRegistryName(),
            new ItemStack(JinxItems.itemTorchArrow, 1),
            new ItemStack(Blocks.TORCH, 1),
            Items.ARROW)
            .setRegistryName(new ResourceLocation(Jinx.MODID, "torcharrow_recipe"));

    private IRecipe smallQuiverRecipe = new ShapedOreRecipe(
            JinxItems.itemSmallQuiver.getRegistryName(),
            new ItemStack(JinxItems.itemSmallQuiver, 1),
            new Object[]

                    {
                            " I ",
                            "LAL",
                            Character.valueOf('A'), Items.ARROW,
                            Character.valueOf('I'), "ingotIron",
                            Character.valueOf('L'), "leather"
                    })
            .setRegistryName(new ResourceLocation(Jinx.MODID, "small_quiver_recipe"));

    private IRecipe middleQuiverRecipe = new ShapedOreRecipe(
            JinxItems.itemMiddleQuiver.getRegistryName(),
            new ItemStack(JinxItems.itemMiddleQuiver, 1),
            new Object[]

                    {
                            " I ",
                            "LAL",
                            "LLL",
                            Character.valueOf('A'), Items.ARROW,
                            Character.valueOf('I'), "ingotIron",
                            Character.valueOf('L'), "leather"
                    })
            .setRegistryName(new ResourceLocation(Jinx.MODID, "middle_quiver_recipe"));

    private IRecipe largeQuiverRecipe = new ShapedOreRecipe(
            JinxItems.itemLargeQuiver.getRegistryName(),
            new ItemStack(JinxItems.itemLargeQuiver, 1),
            new Object[]

                    {
                            "LIL",
                            "LAL",
                            "LLL",
                            Character.valueOf('A'), Items.ARROW,
                            Character.valueOf('I'), "ingotIron",
                            Character.valueOf('L'), "leather"
                    })
            .setRegistryName(new ResourceLocation(Jinx.MODID, "large_quiver_recipe"));

    private IRecipe seedBagRecipe = new ShapedOreRecipe(
            JinxItems.itemSeedBag.getRegistryName(),
            new ItemStack(JinxItems.itemSeedBag, 1),
            new Object[]

                    {
                            "PPP",
                            "PSP",
                            "PPP",
                            Character.valueOf('P'), "paper",
                            Character.valueOf('S'), Items.WHEAT_SEEDS
                    })
            .setRegistryName(new ResourceLocation(Jinx.MODID, "seed_bag_recipe"));

    private IRecipe lampRecipe = new ShapedOreRecipe(
            JinxBlocks.blockLamp.getRegistryName(),
            new ItemStack(JinxBlocks.itemBlockLamp, 4),
            new Object[]

                    {
                            "IPI",
                            "PCP",
                            "IPI",
                            Character.valueOf('I'), "ingotIron",
                            Character.valueOf('P'), "paneGlass",
                            Character.valueOf('C'), Items.COAL
                    })
            .setRegistryName(new ResourceLocation(Jinx.MODID, "lamp_recipe"));

    private IRecipe steadyLadderRecipe = new ShapedOreRecipe(
            JinxBlocks.blockSteadyLadder.getRegistryName(),
            new ItemStack(JinxBlocks.itemBlockSteadyLadder, 4),
            new Object[]

                    {
                            "W W",
                            "WWW",
                            "W W",
                            Character.valueOf('W'), "slabWood"
                    })
            .setRegistryName(new ResourceLocation(Jinx.MODID, "steady_ladder_recipe"));

    @SubscribeEvent
    public void register(RegistryEvent.Register<IRecipe> event) {
        IForgeRegistry<IRecipe> registry = event.getRegistry();

        registry.register(leatherLongbowRecipe);
        registry.register(compositeBowRecipe);
        registry.register(torchArrowRecipe);
        registry.register(smallQuiverRecipe);
        registry.register(middleQuiverRecipe);
        registry.register(largeQuiverRecipe);
        registry.register(seedBagRecipe);
        registry.register(lampRecipe);
        registry.register(steadyLadderRecipe);
    }

}
