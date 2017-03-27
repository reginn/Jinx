package com.rgn.jinx.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class EquipArrowInfoMessage implements IMessage {

    protected int arrowIndex;

    public EquipArrowInfoMessage() {
    }

    public EquipArrowInfoMessage(int _arrowIndex) {
        this.arrowIndex = _arrowIndex;
    }

    public int getArrowIndex() {
        return this.arrowIndex;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.arrowIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.arrowIndex);
    }
}
