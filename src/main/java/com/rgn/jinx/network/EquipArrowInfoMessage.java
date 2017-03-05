package com.rgn.jinx.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class EquipArrowInfoMessage implements IMessage {

    protected int ammoIndex;
    protected int slotIndex;

    public EquipArrowInfoMessage() {
    }

    public EquipArrowInfoMessage(int _ammoIndex, int _slotIndex) {
        this.ammoIndex = _ammoIndex;
        this.slotIndex = _slotIndex;
    }

    public int getAmmoIndex() {
        return this.ammoIndex;
    }

    public int getSlotIndex() {
        return this.slotIndex;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.ammoIndex = buf.readInt();
        this.slotIndex = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.ammoIndex);
        buf.writeInt(this.slotIndex);
    }
}
