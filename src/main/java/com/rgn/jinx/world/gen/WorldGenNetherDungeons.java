package com.rgn.jinx.world.gen;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenNetherDungeons extends WorldGenDungeonsBase {

    public WorldGenNetherDungeons() {
        super("Blaze", 3, 3);
    }

    @Override
    protected void setBaseBlock(World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, Blocks.NETHERRACK.getDefaultState(), 2);
    }

    @Override
    void setRandomBlock(World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState(), 2);
    }
}
