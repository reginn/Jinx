package com.rgn.jinx.network;

import com.rgn.jinx.item.ItemElvenBow;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Reginn666 on 2017/03/05.
 */
public class EquipArrowInfoMessageHandler implements IMessageHandler<EquipArrowInfoMessage, IMessage> {


    @Override
    public IMessage onMessage(EquipArrowInfoMessage message, MessageContext ctx) {

        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        ItemStack heldItem = player.getHeldItemMainhand();

        int ammoIndex = message.getAmmoIndex();
        int slotIndex = message.getSlotIndex();

        if (heldItem.getItem() instanceof ItemElvenBow) {
            ((ItemElvenBow) heldItem.getItem()).writeItemStackToNBT(heldItem, ammoIndex, slotIndex);
        }

        return null;
    }
}
