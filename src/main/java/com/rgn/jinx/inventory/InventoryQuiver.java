package com.rgn.jinx.inventory;

import com.google.common.collect.Lists;
import com.rgn.jinx.item.EnumQuiverSize;
import com.rgn.jinx.item.ItemElvenBow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;
import java.util.List;

public class InventoryQuiver implements IInventory {

    protected EnumQuiverSize quiverSize;
    protected ItemStack quiver;
    protected List<ItemStack> arrows = Lists.newLinkedList();

    public InventoryQuiver(EnumQuiverSize quiverSize, ItemStack itemStack) {
        this.quiverSize = quiverSize;
        this.quiver = itemStack;
    }

    @Override
    public int getSizeInventory() {
        return this.quiverSize.getInventorySize();
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return arrows.size() > index ? arrows.get(index) : null;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return null;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {

    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }
}
