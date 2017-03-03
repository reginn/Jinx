package com.rgn.jinx.item;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.entity.projectile.EntityElvenArrow;
import com.rgn.jinx.init.JinxItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Reginn666 on 2017/03/03.
 */
public class ItemElvenArrow extends Item {

    protected EnumElvenArrowType arrowType;

    public ItemElvenArrow(EnumElvenArrowType _arrowType) {
        super();

        this.arrowType = _arrowType;
    }

    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        return new EntityElvenArrow(worldIn, shooter);
    }

    public boolean isInfinite(ItemStack itemstack, ItemStack stack, EntityPlayer entityplayer) {
        int enchant = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.INFINITY, stack);
        return enchant <= 0 ? false : this.getClass() == ItemElvenArrow.class;
    }


//    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.entity.player.EntityPlayer player)
//    {
//        int enchant = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.INFINITY, bow);
//        return enchant <= 0 ? false : this.getClass() == ItemArrow.class;
//    }

}
