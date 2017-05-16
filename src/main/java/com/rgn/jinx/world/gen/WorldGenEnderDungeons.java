package com.rgn.jinx.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenEnderDungeons extends WorldGenDungeonsBase {

    public WorldGenEnderDungeons() {
        super("Enderman", 3, 3);
    }

    @Override
    protected void setBaseBlock(World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, Blocks.STONEBRICK.getDefaultState(), 2);
    }

    @Override
    void setRandomBlock(World worldIn, BlockPos pos) {
        worldIn.setBlockState(pos, Blocks.STONEBRICK.getStateFromMeta(BlockStoneBrick.CRACKED_META), 2);
    }
}
