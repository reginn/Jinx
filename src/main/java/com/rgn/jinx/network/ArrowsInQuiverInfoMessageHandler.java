package com.rgn.jinx.network;

import com.rgn.jinx.item.ItemQuiver;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ArrowsInQuiverInfoMessageHandler implements IMessageHandler<ArrowsInQuiverInfoMessage, IMessage> {

    @Override
    public IMessage onMessage(ArrowsInQuiverInfoMessage message, MessageContext ctx) {

        EntityPlayerMP player = ctx.getServerHandler().player;
        ItemStack heldQuiver = player.getHeldItemMainhand();

        ItemStack clientQuiver = message.getQuiver();

        if (!heldQuiver.isEmpty() && heldQuiver.getItem() instanceof ItemQuiver) {
            heldQuiver.setTagCompound(clientQuiver.getTagCompound());
        }

        return null;
    }

}
