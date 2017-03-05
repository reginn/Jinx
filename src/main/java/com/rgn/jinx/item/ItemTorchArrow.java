package com.rgn.jinx.item;

import com.rgn.jinx.entity.projectile.EntityTorchArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by Reginn666 on 2017/03/03.
 */
public class ItemTorchArrow extends ItemElvenArrow {
    public ItemTorchArrow(EnumElvenArrowType type) {
        super(type);
    }

    @Override
    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
        return new EntityTorchArrow(worldIn, shooter);
    }
}
