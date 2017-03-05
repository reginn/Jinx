package com.rgn.jinx.item;

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

}