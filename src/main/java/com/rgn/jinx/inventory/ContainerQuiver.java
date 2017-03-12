package com.rgn.jinx.inventory;

import com.rgn.jinx.item.EnumQuiverSize;
import com.rgn.jinx.item.ItemQuiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ContainerQuiver extends Container {

    private final IInventory inventoryQuiver;
    private final IInventory playerInventory;
    private final EnumQuiverSize quiverSize;

    public ContainerQuiver(World world, EntityPlayer entityPlayer, ItemStack itemStack) {
        this.playerInventory = entityPlayer.inventory;
        this.inventoryQuiver = new InventoryQuiver(itemStack);
        this.quiverSize = ((ItemQuiver) itemStack.getItem()).getQuiverSize();


        for (int col = 0; col < this.quiverSize.getColSize(); ++col) {
            for (int row = 0; row < this.quiverSize.getRowSize(); ++row) {
                this.addSlotToContainer(new Slot(this.inventoryQuiver, row + col * 3, this.quiverSize.getXStart() + row * 18, 17 + col * 18));
            }
        }

        for (int col = 0; col < 3; ++col) {
            for (int row = 0; row < 9; ++row) {
                this.addSlotToContainer(new Slot(this.playerInventory, row + col * 9 + 9, 8 + row * 18, 84 + col * 18));
            }
        }

        for (int row = 0; row < 9; ++row) {
            this.addSlotToContainer(new Slot(this.playerInventory, row, 8 + row * 18, 142));
        }

    }

    public IInventory getInventoryQuiver() {
        return this.inventoryQuiver;
    }

    public IInventory getPlayerInventory() {
        return this.playerInventory;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.inventoryQuiver.isUseableByPlayer(playerIn);
    }


    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        this.inventoryQuiver.closeInventory(playerIn);
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack temp = slot.getStack();
            itemstack = temp.copy();
            if (index < this.inventoryQuiver.getSizeInventory()) {
                if (!mergeItemStack(temp, this.inventoryQuiver.getSizeInventory(), inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.inventoryQuiver.isItemValidForSlot(index, temp)) {
                return null;
            } else if (!mergeItemStack(temp, 0, this.inventoryQuiver.getSizeInventory(), false)) {
                return null;
            }

            if (temp.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (temp.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerIn, temp);
        }

        return itemstack;
    }
}
