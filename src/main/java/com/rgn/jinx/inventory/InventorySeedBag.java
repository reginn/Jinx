package com.rgn.jinx.inventory;

import com.rgn.jinx.init.JinxTranslations;
import com.rgn.jinx.item.ItemSeedBag;
import com.rgn.jinx.network.SeedBagInfoMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class InventorySeedBag extends ItemInventory {

    public InventorySeedBag(ItemStack stack) {
        super(stack, 9, JinxTranslations.INVENTORY_SEEDBAG);
    }

    @Override
    public IMessage getMessage(ItemStack itemStack) {
        return new SeedBagInfoMessage(itemStack);
    }

    @Override
    protected boolean isUsableItem(EntityPlayer player) {
        return player.getHeldItemMainhand().getItem() instanceof ItemSeedBag;
    }
}
