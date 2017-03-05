package com.rgn.jinx.event;

import akka.japi.Pair;
import com.google.common.collect.Lists;
import com.rgn.jinx.init.JinxMessages;
import com.rgn.jinx.item.ItemElvenBow;
import com.rgn.jinx.network.EquipArrowInfoMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/**
 * Created by Reginn666 on 2017/03/04.
 */
public class ForgeEvents {

    @SubscribeEvent
    public void changeEquipArrow(PlayerInteractEvent.LeftClickEmpty event) {

        EntityPlayer player = event.getEntityPlayer();
        ItemStack itemStack = event.getItemStack();
        NBTTagCompound tag = itemStack.getTagCompound();
        List<Pair<ItemStack, Integer>> ammoList = Lists.newArrayList();

        if (player.isSneaking() && event.getSide().isClient()) {
            ItemElvenBow bow = (ItemElvenBow) itemStack.getItem();

            ammoList = bow.createAmmoList(player);

            if (ammoList != null) {

                int ammoIndex = bow.readNBTTagCompoundFromItemStack(itemStack).first();
                int slotIndex = bow.readNBTTagCompoundFromItemStack(itemStack).second();

                ammoIndex++;
                ammoIndex %= ammoList.size();

                player.addChatMessage(new TextComponentString("" + ammoIndex));

                bow.writeItemStackToNBT(itemStack, ammoIndex, slotIndex);

                JinxMessages.networkWrapper.sendToServer(new EquipArrowInfoMessage(ammoIndex, slotIndex));

            }
        }

    }

}
