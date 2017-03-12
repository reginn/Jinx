package com.rgn.jinx.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ArrowsInQuiverInfoMessage implements IMessage {

    private ItemStack quiver;

    public ArrowsInQuiverInfoMessage() {}

    public ArrowsInQuiverInfoMessage(ItemStack quiver) {
        this.quiver = quiver;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.quiver = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.quiver);
    }

    public ItemStack getQuiver() {
        return this.quiver;
    }
}
