package com.rgn.jinx.item;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.init.JinxConstants;
import com.rgn.jinx.init.JinxTranslations;
import com.rgn.jinx.inventory.InventoryQuiver;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemQuiver extends ItemElvenArrow {

    protected EnumQuiverSize quiverSize;

    public ItemQuiver(EnumQuiverSize quiverSize) {
        super(EnumElvenArrowType.QUIVER);
        this.quiverSize = quiverSize;
        this.setMaxStackSize(1);
    }


    @Override
    public EntityArrow createArrow(World worldIn, ItemStack quiver, EntityLivingBase shooter) {

        EntityArrow entityArrow = null;

        if (!quiver.isEmpty() && quiver.hasTagCompound()) {
            IInventory inventoryQuiver = new InventoryQuiver(quiver);

            for (int i = 0; i < inventoryQuiver.getSizeInventory(); i++) {
                ItemStack arrow = inventoryQuiver.getStackInSlot(i);

                if (!arrow.isEmpty()) {
                    entityArrow = ((ItemArrow) arrow.getItem()).createArrow(worldIn, arrow, shooter);

                    boolean isArrowInfinity = ((EntityPlayer) shooter).capabilities.isCreativeMode
                            || (arrow.getItem() instanceof ItemArrow && ((ItemArrow) arrow.getItem()).isInfinite(arrow, quiver, (EntityPlayer) shooter));

                    if (!isArrowInfinity) {
                        arrow.shrink(1);
                        inventoryQuiver.closeInventory((EntityPlayer) shooter);
                    }
                    break;
                }
            }
        }

        return entityArrow;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {

        ItemStack itemStackIn = playerIn.getHeldItemMainhand();
        if (!itemStackIn.isEmpty() && itemStackIn.getItem() instanceof ItemQuiver &&
                playerIn != null && playerIn.isSneaking()) {

            playerIn.openGui(Jinx.instance, JinxConstants.getGuiID(this.quiverSize), worldIn, 0, 0, 0);

        }

        return super.onItemRightClick(worldIn, playerIn, hand);
    }

    @Override
    public void addInformation(ItemStack quiver, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        TextComponentTranslation arrowType = new TextComponentTranslation(JinxTranslations.ARROW_TYPE_IN_QUIVER);
        TextComponentTranslation stackSize = new TextComponentTranslation(JinxTranslations.ARROW_STACKSIZE_IN_QUIVER);
        TextComponentTranslation empty = new TextComponentTranslation(JinxTranslations.EMPTY_QUIVER);

        ItemStack arrow = this.getItemStackFromNBT(quiver);
        tooltip.add(arrowType.getFormattedText() + " : " + (!arrow.isEmpty() ? arrow.getDisplayName() : empty.getFormattedText()));
        tooltip.add(stackSize.getFormattedText() + " : " + (!arrow.isEmpty() ? this.getArrowStackSize(quiver) : "0"));

    }

    public int getArrowStackSize(ItemStack quiver) {
        IInventory inventoryQuiver = new InventoryQuiver(quiver);
        ItemStack arrow;
        int stackSize = 0;

        for (int i = 0; i < inventoryQuiver.getSizeInventory(); ++i) {
            arrow = inventoryQuiver.getStackInSlot(i);
            if (!arrow.isEmpty()) {
                stackSize += arrow.getCount();
            }
        }
        return stackSize;
    }

    public EnumQuiverSize getQuiverSize() {
        return this.quiverSize;
    }


    @Override
    public String getItemStackDisplayName(ItemStack quiver) {

        ItemStack arrow = getItemStackFromNBT(quiver);
        String arrowType = "(empty)";

        if (!arrow.isEmpty()) {
            arrowType = "(" + arrow.getDisplayName() + ")";
        }

        return super.getItemStackDisplayName(quiver) + arrowType;
    }

    @Nullable
    protected ItemStack getItemStackFromNBT(ItemStack quiver) {

        IInventory inventoryQuiver = new InventoryQuiver(quiver);
        ItemStack arrow = ItemStack.EMPTY;

        for (int i = 0; i < inventoryQuiver.getSizeInventory(); i++) {
            arrow = inventoryQuiver.getStackInSlot(i);
            if (!arrow.isEmpty()) {
                break;
            }
        }
        return arrow;

    }


}
