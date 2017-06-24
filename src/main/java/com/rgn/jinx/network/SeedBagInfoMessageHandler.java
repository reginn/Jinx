package com.rgn.jinx.network;

import com.rgn.jinx.item.ItemSeedBag;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SeedBagInfoMessageHandler implements IMessageHandler<SeedBagInfoMessage, IMessage> {

    @Override
    public IMessage onMessage(SeedBagInfoMessage message, MessageContext ctx) {

        EntityPlayerMP player = ctx.getServerHandler().player;
        ItemStack heldSeedBag = player.getHeldItemMainhand();

        ItemStack clientSeedBag = message.getSeedBag();

        if (!heldSeedBag.isEmpty() && heldSeedBag.getItem() instanceof ItemSeedBag) {
            heldSeedBag.setTagCompound(clientSeedBag.getTagCompound());
        }

        return null;
    }

}
