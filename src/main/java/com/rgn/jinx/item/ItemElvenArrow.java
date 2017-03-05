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
public abstract class ItemElvenArrow extends ItemArrow {

    protected EnumElvenArrowType arrowType;

    public ItemElvenArrow(EnumElvenArrowType _arrowType) {
        super();

        this.arrowType = _arrowType;
    }

    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        return new EntityElvenArrow(worldIn, shooter);
    }

}
