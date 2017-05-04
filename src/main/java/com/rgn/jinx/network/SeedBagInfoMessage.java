package com.rgn.jinx.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SeedBagInfoMessage implements IMessage {

    private ItemStack seedBag;

    public SeedBagInfoMessage() {}

    public SeedBagInfoMessage(ItemStack seedBag) {
        this.seedBag = seedBag;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.seedBag = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, this.seedBag);
    }

    public ItemStack getSeedBag() {
        return this.seedBag;
    }
}
