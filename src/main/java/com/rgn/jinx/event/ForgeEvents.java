package com.rgn.jinx.event;

import akka.japi.Pair;
import com.google.common.collect.Lists;
import com.rgn.jinx.init.JinxMessages;
import com.rgn.jinx.item.ItemElvenBow;
import com.rgn.jinx.network.EquipArrowInfoMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ForgeEvents {

    @SubscribeEvent
    public void changeEquipArrow(PlayerInteractEvent.LeftClickEmpty event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack mainHandItemStack = event.getItemStack();
        ItemStack offHandItemStack = player.getHeldItemOffhand();

        if (mainHandItemStack == null && offHandItemStack == null) {
            return ;
        }

        if (mainHandItemStack != null && mainHandItemStack.getItem() instanceof ItemArrow) {
            // if main hand item is arrow, end it.
            return ;
        }

        if (hasBow(mainHandItemStack) || hasBow(offHandItemStack)) {

            ItemStack equippedBow = getEquippedBow(player);

            if (player.isSneaking() && event.getSide().isClient()) {
                NBTTagCompound tag = equippedBow.getTagCompound();
                ItemElvenBow bow = (ItemElvenBow) equippedBow.getItem();
                List<Pair<ItemStack, Integer>> ammoList = bow.createAmmoList(player);

                if (ammoList != null) {

                    int ammoIndex = bow.readNBTTagCompoundFromItemStack(equippedBow).first();
                    int slotIndex = bow.readNBTTagCompoundFromItemStack(equippedBow).second();

                    ammoIndex++;
                    ammoIndex %= ammoList.size();

                    bow.informEquipArrow(player, ammoList.get(ammoIndex).first());
                    bow.writeItemStackToNBT(equippedBow, ammoIndex, slotIndex);

                    // sync client-server itemstack
                    JinxMessages.networkWrapper.sendToServer(new EquipArrowInfoMessage(ammoIndex, slotIndex));

                }
            }
        }

    }

    protected boolean hasBow(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof ItemElvenBow;
    }

    protected ItemStack getEquippedBow(EntityPlayer player) {

        ItemStack mainHandItemStack = player.getHeldItemMainhand();
        ItemStack offHandItemStack = player.getHeldItemOffhand();

        return hasBow(mainHandItemStack) ? mainHandItemStack : offHandItemStack;

    }

}
