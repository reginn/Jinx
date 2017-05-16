package com.rgn.jinx.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ContainerSeedBag extends Container {

    private final IInventory inventorySeedBag;
    private final IInventory playerInventory;

    public ContainerSeedBag(World world, EntityPlayer entityPlayer, ItemStack itemStack) {
        this.playerInventory = entityPlayer.inventory;
        this.inventorySeedBag = new InventorySeedBag(itemStack);

        for (int col = 0; col < 3; ++col) {
            for (int row = 0; row < 3; ++row) {
                this.addSlotToContainer(
                        new SlotSeedBag(this.inventorySeedBag
                                , row + 3 * col
                                , 62 + row * 18
                                , 17 + col * 18));
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

    public IInventory getInventorySeedBag() {
        return this.inventorySeedBag;
    }

    public IInventory getPlayerInventory() {
        return this.playerInventory;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.inventorySeedBag.isUsableByPlayer(playerIn);
    }


    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        this.inventorySeedBag.closeInventory(playerIn);
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack temp = slot.getStack();
            itemstack = temp.copy();
            if (index < inventorySeedBag.getSizeInventory()) {
                if (!mergeItemStack(temp, this.inventorySeedBag.getSizeInventory(), inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!slot.isItemValid(temp)) {
                return ItemStack.EMPTY;
            } else if (!mergeItemStack(temp, 0, this.inventorySeedBag.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }

            if (temp.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (temp.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, temp);
        }

        return itemstack;
    }


}
