package com.rgn.jinx.network;

import com.rgn.jinx.client.gui.GuiQuiver;
import com.rgn.jinx.client.gui.GuiSeedBag;
import com.rgn.jinx.init.JinxConstants;
import com.rgn.jinx.inventory.ContainerQuiver;
import com.rgn.jinx.inventory.ContainerSeedBag;
import com.rgn.jinx.item.ItemQuiver;
import com.rgn.jinx.item.ItemSeedBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        ItemStack heldItem = player.getHeldItemMainhand();

        if (!heldItem.isEmpty()) {
            if (heldItem.getItem() instanceof ItemQuiver && ID == JinxConstants.getGuiID(((ItemQuiver) heldItem.getItem()).getQuiverSize())) {
                return new ContainerQuiver(world, player, heldItem);
            } else if (heldItem.getItem() instanceof ItemSeedBag && ID == JinxConstants.GUI_ID_SEED_BAG) {
                return new ContainerSeedBag(world, player, heldItem);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ItemStack heldItem = player.getHeldItemMainhand();

        if (!heldItem.isEmpty()) {
            if (heldItem.getItem() instanceof ItemQuiver && ID == JinxConstants.getGuiID(((ItemQuiver) heldItem.getItem()).getQuiverSize())) {
                return new GuiQuiver(world, player, heldItem);
            } else if (heldItem.getItem() instanceof ItemSeedBag && ID == JinxConstants.GUI_ID_SEED_BAG) {
                return new GuiSeedBag(world, player, heldItem);
            }
        }
        return null;
    }
}
