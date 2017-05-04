package com.rgn.jinx.inventory;

import com.rgn.jinx.init.JinxTranslations;
import com.rgn.jinx.item.ItemQuiver;
import com.rgn.jinx.network.ArrowsInQuiverInfoMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nonnull;

public class InventoryQuiver extends ItemInventory {

    public InventoryQuiver(ItemStack itemStack) {
        super(itemStack, ((ItemQuiver) itemStack.getItem()).getQuiverSize().getInventorySize(), JinxTranslations.INVENTORY_QUIVER);
    }

    protected boolean isSamePotionArrow(@Nonnull ItemStack slot, @Nonnull ItemStack insertItem) {
        return PotionUtils.getPotionFromItem(slot) == PotionUtils.getPotionFromItem(insertItem);
    }

    @Override
    public IMessage getMessage(ItemStack itemStack) {
        return new ArrowsInQuiverInfoMessage(itemStack);
    }

    @Override
    protected boolean isSameItem(ItemStack slotItem, ItemStack insertItem) {
        return super.isSameItem(slotItem, insertItem) && isSamePotionArrow(slotItem, insertItem);
    }

    @Override
    public boolean isUseableByPlayer(@Nonnull EntityPlayer player) {
        return player.getHeldItemMainhand().getItem() instanceof ItemQuiver;
    }
}
