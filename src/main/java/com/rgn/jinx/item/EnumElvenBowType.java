package com.rgn.jinx.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Reginn666 on 2017/03/03.
 */
public enum EnumElvenBowType {
    /*
        Type(SpriteID, durability, baseDamage, velocityRatio, enchantability)
    */
    //VANILA( 0,  384, 2.0F, 1.00F,  1),
    LEATHER  ( 512, 2.0D, 5.00F, 15),
    COMPOSITE( 768, 3.0D, 4.00F, 10),
    ENHANCED (2048, 2.0D, 1.50F,  1),
    BONE     (  65, 4.0D, 1.00F,  0),
    SHADOW   ( 128, 2.0D, 1.00F,  0),
    END      ( 256, 2.0D, 1.00F,  0),
    FEATHER  ( 256, 2.0D, 1.00F,  0),
    STEEL    (2048, 4.0D, 1.50F,  1),
    ELVEN    (1024, 2.0D, 1.00F,  1);

    private int durability;
    private double baseDamage;
    private float velocityRatio;
    private int enchantability;

//    @SideOnly(Side.CLIENT)
//    private Icon icon;
//    @SideOnly(Side.CLIENT)
//    private Icon animation[];

    private EnumElvenBowType(int durability, double baseDamage, float velocityRatio, int enchantability) {
        this.durability = durability;
        this.baseDamage = baseDamage;
        this.velocityRatio = velocityRatio;
        this.enchantability = enchantability;
    }

    public int getDurability() {
        return this.durability;
    }

    public double getBaseDamage() {
        return this.baseDamage;
    }

    public float getVelocityRatio() {
        return this.velocityRatio;
    }

    public int getEnchantability() {
        return this.enchantability;
    }
//
//    @SideOnly(Side.CLIENT)
//    public void createIcon(IconRegister iconRegister) {
//        this.animation = new Icon[3];
//        this.icon = iconRegister.registerIcon(String.format("rgn.elventools:%s%d", name().toLowerCase(), 0));
//        for (int i = 0; i < 3; ++i) {
//            this.animation[i] = iconRegister.registerIcon(String.format("rgn.elventools:%s%d", name().toLowerCase(), i + 1));
//        }
//    }
//
//    @SideOnly(Side.CLIENT)
//    public Icon getBaseIcon() {
//        return this.icon;
//    }
//
//    @SideOnly(Side.CLIENT)
//    public Icon[] getAnimation() {
//        return this.animation;
//    }

}
