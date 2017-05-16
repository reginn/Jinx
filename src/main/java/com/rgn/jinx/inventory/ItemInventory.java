package com.rgn.jinx.inventory;

import com.google.common.collect.Lists;
import com.rgn.jinx.init.JinxMessages;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemInventory implements IInventory {

    private final ItemStack heldItem;
    private final int slotSize;
    private NonNullList<ItemStack> slots;
    private String defaultName;
    private String customName;

    public ItemInventory(ItemStack itemStack, int slotSize, String defaultName) {
        this.heldItem = itemStack;
        this.slotSize = slotSize;
        this.slots = NonNullList.<ItemStack>withSize(slotSize, ItemStack.EMPTY);
        this.defaultName = defaultName;
        this.readFromNBT(itemStack.getTagCompound());
    }

    @Override
    public int getSizeInventory() {
        return this.slots.size();
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < this.slots.size() ? (ItemStack)this.slots.get(index) : ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int amount) {
        ItemStack slot = ItemStackHelper.getAndSplit(this.slots, index, amount);

        if (slot != null) {
            this.markDirty();
        }

        return slot;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.slots, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        this.slots.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        NBTTagCompound nbtTagCompound = this.heldItem.getTagCompound();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }

        this.writeToNBT(nbtTagCompound);
        this.heldItem.setTagCompound(nbtTagCompound);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            JinxMessages.networkWrapper.sendToServer(this.getMessage(this.heldItem));
        }
    }

    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        return player.getHeldItemMainhand() != null
                && isUsableItem(player);
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {
        this.markDirty();
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack insertItem) {
        return this.isSameItem(this.getStackInSlot(index), insertItem);
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
        this.slots.clear();
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.slots)
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : this.defaultName;
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null;
    }

    @Override
    public ITextComponent getDisplayName() {
        return (ITextComponent) (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    protected void readFromNBT(NBTTagCompound nbtTagCompound) {
        this.slots = NonNullList.withSize(this.slotSize, ItemStack.EMPTY);

        if (nbtTagCompound != null) {
            int size = nbtTagCompound.getInteger("Size");
            NBTTagList nbtTagList = nbtTagCompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < nbtTagList.tagCount(); i++) {
                NBTTagCompound slotNbtTagCompound = (NBTTagCompound) nbtTagList.getCompoundTagAt(i);
                int j = slotNbtTagCompound.getByte("Slot") & 0xff;
                if (j >= 0 && j < this.getSizeInventory()) {
                    this.slots.set(j, new ItemStack(slotNbtTagCompound));
                }
            }
        }
    }

    protected void writeToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.slots.get(i) != null) {
                NBTTagCompound slotNbtTagCompound = new NBTTagCompound();
                slotNbtTagCompound.setByte("Slot", (byte) i);
                this.slots.get(i).writeToNBT(slotNbtTagCompound);
                nbtTagList.appendTag(slotNbtTagCompound);
            }
        }

        nbtTagCompound.setTag("Items", nbtTagList);
        nbtTagCompound.setInteger("Size", this.slots.size());
    }

    protected boolean isSameItem(ItemStack slotItem, ItemStack insertItem) {
        return slotItem.getItem() == insertItem.getItem();
    }

    protected boolean isUsableItem(EntityPlayer player) {
        return false;
    }

    public abstract IMessage getMessage(ItemStack itemStack);
}
