package com.rgn.jinx.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class SlotQuiver extends Slot {
    public SlotQuiver(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nullable ItemStack stack) {
        ItemStack arrow;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            arrow = inventory.getStackInSlot(i);
            if (arrow == null) {
                continue;
            }
            return inventory.isItemValidForSlot(i, stack);
        }
        return stack.getItem() instanceof ItemArrow;
    }
}
