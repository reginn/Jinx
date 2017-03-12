package com.rgn.jinx.network;

import com.rgn.jinx.client.gui.GuiQuiver;
import com.rgn.jinx.init.JinxConstants;
import com.rgn.jinx.inventory.ContainerQuiver;
import com.rgn.jinx.item.ItemQuiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        ItemStack quiver = player.getHeldItemMainhand();

        if (quiver != null && quiver.getItem() instanceof ItemQuiver && ID == JinxConstants.getGuiID(((ItemQuiver) quiver.getItem()).getQuiverSize())) {
            return new ContainerQuiver(world, player, quiver);
        } else {
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ItemStack quiver = player.getHeldItemMainhand();

        if (quiver != null && quiver.getItem() instanceof ItemQuiver && ID == JinxConstants.getGuiID(((ItemQuiver) quiver.getItem()).getQuiverSize())) {
            return new GuiQuiver(world, player, quiver);
        } else {
            return null;
        }
    }
}
