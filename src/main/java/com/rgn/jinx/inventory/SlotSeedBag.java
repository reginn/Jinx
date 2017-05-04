package com.rgn.jinx.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;

public class SlotSeedBag extends Slot {
    public SlotSeedBag(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack insertItem) {
        ItemStack seed;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            seed = inventory.getStackInSlot(i);
            if (seed == null) {
                continue;
            }
            return inventory.isItemValidForSlot(i, insertItem);
        }
        return insertItem.getItem() instanceof IPlantable;
    }
}
