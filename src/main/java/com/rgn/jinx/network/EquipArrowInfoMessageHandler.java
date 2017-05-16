package com.rgn.jinx.network;

import com.rgn.jinx.item.ItemElvenBow;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EquipArrowInfoMessageHandler implements IMessageHandler<EquipArrowInfoMessage, IMessage> {

    @Override
    public IMessage onMessage(EquipArrowInfoMessage message, MessageContext ctx) {

        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        ItemStack bow = player.getHeldItemMainhand();

        if (bow.isEmpty() || !(bow.getItem() instanceof ItemElvenBow)) {
            bow = player.getHeldItemOffhand();
        }

        int arrowIndex = message.getArrowIndex();

        if (bow.getItem() instanceof ItemElvenBow) {
            ((ItemElvenBow) bow.getItem()).writeArrowIndexToItemStackNBT(bow, arrowIndex);
        }

        return null;
    }
}
