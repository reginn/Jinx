package com.rgn.jinx.world.gen;

import com.rgn.jinx.Jinx;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.IWorldGenerator;
import org.apache.logging.log4j.LogManager;

import java.util.Random;

public class JinxWorldGenerator implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()) {
            case -1:
                this.generateNether(world, random, chunkX << 4, chunkZ << 4);
                break;
            case 1:
                // this.generateEnd(world. random, chunkX << 4, chunkZ << 4);
                break;
            default:
                this.generateSurface(world, random, chunkX << 4, chunkZ << 4);
        }
    }

    private void generateSurface(World world, Random random, int chunkX, int chunkZ) {
        {
            int x = chunkX + random.nextInt(16) + 8;
            int y = random.nextInt(40) + 10;
            int z = chunkZ + random.nextInt(16) + 8;
            boolean isGen = (new WorldGenNetherDungeons()).generate(world, random, new BlockPos(x, y, z));

            if (isGen) {
                LogManager.getLogger().info("Blaze Spawn at ({}, {}, {})"
                        , new Object[]{
                                Integer.valueOf(x)
                                , Integer.valueOf(y)
                                , Integer.valueOf(z)});
            }
        }

        {
            int x = chunkX + random.nextInt(16) + 8;
            int y = random.nextInt(40) + 10;
            int z = chunkZ + random.nextInt(16) + 8;
            boolean isGen = (new WorldGenEnderDungeons()).generate(world, random, new BlockPos(x, y, z));
            if (isGen) {
                LogManager.getLogger().info("Enderman Spawn at ({}, {}, {})"
                        , new Object[]{
                                Integer.valueOf(x)
                                , Integer.valueOf(y)
                                , Integer.valueOf(z)});
            }

        }

    }

    private void generateNether(World world, Random random, int chunkX, int chunkZ) {
        {
            int x = chunkX + random.nextInt(16) + 8;
            int y = random.nextInt(100) + 50;
            int z = chunkZ + random.nextInt(16) + 8;
            boolean isGen = (new WorldGenQuartzDungeons()).generate(world, random, new BlockPos(x, y, z));
            if (isGen) {
                LogManager.getLogger().info("Skeleton Spawn at ({}, {}, {})"
                        , new Object[]{
                                Integer.valueOf(x)
                                , Integer.valueOf(y)
                                , Integer.valueOf(z)});
            }

        }
    }

}
