package com.rgn.jinx.item;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.init.JinxConstants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemQuiver extends ItemElvenArrow {

    protected EnumQuiverSize quiverSize;

    public ItemQuiver(EnumQuiverSize quiverSize) {
        super(EnumElvenArrowType.QUIVER);
        this.quiverSize = quiverSize;
    }


    @Override
    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {



        return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        if (itemStackIn != null && itemStackIn.getItem() instanceof ItemQuiver &&
                playerIn != null && playerIn.isSneaking()) {

            playerIn.openGui(Jinx.instance, JinxConstants.getGuiID(this.quiverSize), worldIn, 0,0,0 );

        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    public EnumQuiverSize getQuiverSize() {
        return this.quiverSize;
    }

}
