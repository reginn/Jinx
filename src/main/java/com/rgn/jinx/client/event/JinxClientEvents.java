package com.rgn.jinx.client.event;

import com.rgn.jinx.client.render.ItemStackRenderer;
import com.rgn.jinx.init.JinxMessages;
import com.rgn.jinx.item.ItemElvenBow;
import com.rgn.jinx.item.ItemQuiver;
import com.rgn.jinx.network.EquipArrowInfoMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class JinxClientEvents {

    @SubscribeEvent
    public void changeEquipArrow(PlayerInteractEvent.LeftClickEmpty event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack mainHandItemStack = event.getItemStack();
        ItemStack offHandItemStack = player.getHeldItemOffhand();

        if (mainHandItemStack == null && offHandItemStack == null) {
            return;
        }

        if (mainHandItemStack != null && mainHandItemStack.getItem() instanceof ItemArrow) {
            // if main hand item is arrow, end it.
            return;
        }

        if (hasBow(mainHandItemStack) || hasBow(offHandItemStack)) {

            ItemStack equippedBow = getEquippedBow(player);

            if (player.isSneaking() && event.getSide().isClient()) {
                NBTTagCompound tag = equippedBow.getTagCompound();
                ItemElvenBow bow = (ItemElvenBow) equippedBow.getItem();
                List<ItemStack> arrows = bow.createArrowList(player);

                if (arrows != null) {

                    int arrowIndex = bow.readArrowIndexFromItemStackNBT(equippedBow);

                    arrowIndex++;
                    arrowIndex %= arrows.size();

                    bow.informEquipArrow(player, arrows.get(arrowIndex));
                    bow.writeArrowIndexToItemStackNBT(equippedBow, arrowIndex);

                    // sync client-server ItemStack nbt
                    JinxMessages.networkWrapper.sendToServer(new EquipArrowInfoMessage(arrowIndex));

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


    @SubscribeEvent
    public void drawEquippedArrowIcon(RenderGameOverlayEvent.Post event) {

        EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
        ItemStack bow = this.getEquippedBow(player);

        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || bow == null) {
            return;
        }

        ItemStack arrow = this.getEquippedArrow(player, bow);

        if (arrow != null) {

            int x = 16;
            int y = 16;

            ItemStackRenderer.instance().renderItemStackIn2D(arrow, x, y);
            ItemStackRenderer.instance().renderStringOnItemStack(this.getArrowStackSize(arrow), arrow, x, y);
        }
    }

    @Nullable
    protected ItemStack getEquippedElvenBow(@Nonnull EntityPlayer player) {
        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemElvenBow) {
            return player.getHeldItemMainhand();
        } else if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() instanceof ItemElvenBow) {
            return player.getHeldItemOffhand();
        } else {
            return null;
        }
    }

    @Nullable
    protected ItemStack getEquippedArrow(@Nonnull EntityPlayer player, @Nonnull ItemStack bow) {
        List<ItemStack> arrowList = ((ItemElvenBow) bow.getItem()).createArrowList(player);
        return ((ItemElvenBow) bow.getItem()).getEquipArrow(bow, arrowList);
    }

    protected String getArrowStackSize(@Nonnull ItemStack arrow) {
        return arrow.getItem() instanceof ItemQuiver
                ? String.valueOf(((ItemQuiver) arrow.getItem()).getArrowStackSize(arrow))
                : String.valueOf(arrow.stackSize);

    }
}