package com.rgn.jinx.world.gen;

import net.minecraft.block.BlockQuartz;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenQuartzDungeons extends WorldGenDungeonsBase {

    public WorldGenQuartzDungeons() {
        super("skeleton", 3, 3);
    }

    @Override
    protected void setBaseBlock(World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, Blocks.QUARTZ_BLOCK.getDefaultState(), 2);
    }

    @Override
    void setRandomBlock(World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, Blocks.QUARTZ_BLOCK.getStateFromMeta(BlockQuartz.EnumType.CHISELED.getMetadata()), 2);
    }
}
