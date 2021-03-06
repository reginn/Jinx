package com.rgn.jinx.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

import javax.annotation.Nonnull;

public abstract class EntityElvenArrow extends EntityArrow implements IThrowableEntity {

    protected int xTile;
    protected int yTile;
    protected int zTile;
    protected EnumFacing facing;
    protected int metadata;
    protected Block hitBlock;

    public EntityElvenArrow(World worldIn) {
        super(worldIn);
    }

    public EntityElvenArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityElvenArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    @Override
    public Entity getThrower() {
        return this.shootingEntity;
    }

    @Override
    public void setThrower(@Nonnull Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.shootingEntity = entity;
        }
    }

}
