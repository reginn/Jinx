package com.rgn.jinx.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by Reginn666 on 2017/03/01.
 */
public class ItemStatikkShiv extends Item {
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {

        attacker.addChatMessage(new TextComponentString("Hit"));
        return super.hitEntity(stack, target, attacker);
    }
}
