package com.rgn.jinx.inventory;

import com.rgn.jinx.init.JinxMessages;
import com.rgn.jinx.init.JinxTranslations;
import com.rgn.jinx.item.EnumQuiverSize;
import com.rgn.jinx.item.ItemQuiver;
import com.rgn.jinx.network.ArrowsInQuiverInfoMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InventoryQuiver implements IInventory {

    private final EnumQuiverSize quiverSize;
    private final ItemStack quiver;
    private ItemStack[] arrows;
    protected String customName;

    public InventoryQuiver(ItemStack itemStack) {
        this.quiver = itemStack;
        this.quiverSize = ((ItemQuiver)quiver.getItem()).getQuiverSize();
        this.arrows = new ItemStack[this.quiverSize.getInventorySize()];
        this.readFromNBT(this.quiver.getTagCompound());
    }

    @Override
    public int getSizeInventory() {
        return this.quiverSize.getInventorySize();
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return index < this.getSizeInventory() ? arrows[index] : null;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemStack = ItemStackHelper.getAndSplit(this.arrows, index, count);

        if (itemStack != null) {
            this.markDirty();
        }

        return itemStack;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.arrows, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
        this.arrows[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        NBTTagCompound nbtTagCompound = this.quiver.getTagCompound();
        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }
        this.writeToNBT(nbtTagCompound);
        this.quiver.setTagCompound(nbtTagCompound);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            JinxMessages.networkWrapper.sendToServer(new ArrowsInQuiverInfoMessage(this.quiver));
        }
    }

    @Override
    public boolean isUseableByPlayer(@Nonnull EntityPlayer player) {
        return player.getHeldItemMainhand() != null
                && player.getHeldItemMainhand().getItem() instanceof ItemQuiver;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {
        this.markDirty();
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {

        if (stack.getItem() instanceof ItemArrow) {
            for (ItemStack arrow : arrows) {
                if (arrow == null) {
                    continue ;
                }
                return isSameArrow(arrow, stack) && isSamePotionArrow(arrow, stack);
            }
        }
        return isArrow(stack);
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
        for (int i = 0; i < this.getSizeInventory(); i++) {
            this.arrows[i] = null;
        }
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : JinxTranslations.INVENTORY_QUIVER;
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

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        this.arrows = new ItemStack[this.getSizeInventory()];

        if (nbtTagCompound != null) {
            NBTTagList nbtTagList = nbtTagCompound.getTagList("Arrows", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < nbtTagList.tagCount(); i++) {
                NBTTagCompound slotNbtTagCompound = (NBTTagCompound) nbtTagList.getCompoundTagAt(i);
                int j = slotNbtTagCompound.getByte("Slot") & 0xff;
                if (j >= 0 && j < this.getSizeInventory()) {
                    this.arrows[j] = ItemStack.loadItemStackFromNBT(slotNbtTagCompound);
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.arrows[i] != null) {
                NBTTagCompound slotNbtTagCompound = new NBTTagCompound();
                slotNbtTagCompound.setByte("Slot", (byte) i);
                this.arrows[i].writeToNBT(slotNbtTagCompound);
                nbtTagList.appendTag(slotNbtTagCompound);
            }
        }

        nbtTagCompound.setTag("Arrows", nbtTagList);
    }

    protected boolean isArrow(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemArrow;
    }

    protected boolean isSameArrow(@Nonnull ItemStack slot, @Nonnull ItemStack add) {
        return slot.getItem() == add.getItem();
    }

    protected boolean isSamePotionArrow(@Nonnull ItemStack slot, @Nonnull ItemStack add) {
        return PotionUtils.getPotionFromItem(slot) == PotionUtils.getPotionFromItem(add);
    }

}
