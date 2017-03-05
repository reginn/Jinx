package com.rgn.jinx.entity.projectile;

import com.rgn.jinx.entity.projectile.EntityElvenArrow;
import com.rgn.jinx.init.JinxItems;
import com.sun.jna.platform.win32.WinDef;
import com.sun.org.apache.bcel.internal.generic.FADD;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by Reginn666 on 2017/03/03.
 */
public class EntityTorchArrow extends EntityElvenArrow {

    public EntityTorchArrow(World worldIn) {
        super(worldIn);
    }

    public EntityTorchArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityTorchArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }


    @Override
    protected void onHit(RayTraceResult rayTraceResultIn) {
        super.onHit(rayTraceResultIn);

        if (rayTraceResultIn.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = rayTraceResultIn.getBlockPos();
        this.facing = rayTraceResultIn.sideHit;

        if (canPlaceAt(this.worldObj.getBlockState(pos), pos)) {
            if (this.worldObj.setBlockState(pos.offset(facing), Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, facing))) {
                this.setDead();
            }
        }
    }

    protected boolean canPlaceAt(IBlockState iBlockState, BlockPos pos) {

        if (this.worldObj == null || this.facing == null) {
            return false;
        }

        return this.facing != EnumFacing.DOWN
                && iBlockState.getBlock().isSideSolid(iBlockState, this.worldObj, pos, this.facing)
                && this.worldObj.isAirBlock(pos.offset(this.facing));
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(JinxItems.itemTorchArrow);
    }
}
