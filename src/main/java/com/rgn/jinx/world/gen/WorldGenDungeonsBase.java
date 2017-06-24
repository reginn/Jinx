package com.rgn.jinx.world.gen;

import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

public abstract class WorldGenDungeonsBase extends WorldGenerator {

    private static final Logger LOGGER = LogManager.getLogger();

    protected List<ResourceLocation> lootTables = Lists.newArrayList(LootTableList.CHESTS_SIMPLE_DUNGEON
            , LootTableList.CHESTS_END_CITY_TREASURE
            , LootTableList.CHESTS_ABANDONED_MINESHAFT
            , LootTableList.CHESTS_NETHER_BRIDGE
            , LootTableList.CHESTS_STRONGHOLD_CORRIDOR);

    protected ResourceLocation mobType;
    protected int xRange;
    protected int zRange;

    protected WorldGenDungeonsBase(int _xRange, int _zRange) {
        this.xRange = _xRange;
        this.zRange = _zRange;
    }

    protected WorldGenDungeonsBase(String _mobType, int _xRange, int _zRange) {
        this(_xRange, _zRange);
        this.mobType = new ResourceLocation(_mobType);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos pos) {
        int yRange = 3;
        int xRange = rand.nextInt(2) + this.xRange;
        int zRange = rand.nextInt(2) + this.zRange;
        int var9 = 0;
        int x, y, z;

        for (x = - xRange - 1; x <= xRange + 1; x++) {
            for (y = - 1; y <= yRange + 1; y++) {
                for (z = - zRange - 1; z <= zRange + 1; z++) {
                    BlockPos blockPos = pos.add(x, y, z);
                    Material material = worldIn.getBlockState(blockPos).getMaterial();
                    boolean flag = material.isSolid();

                    if (y == -1 && !flag) {
                        return false;
                    }

                    if (y == 4 && !flag) {
                        return false;
                    }

                    if ((x == - xRange - 1 || x == xRange + 1
                            || z ==  - zRange - 1 || z == zRange + 1)
                            && y == 0 && worldIn.isAirBlock(blockPos) && worldIn.isAirBlock(blockPos.up())) {
                        ++var9;
                    }
                }
            }
        }

        if (var9 >= 1 && var9 <= 5) {
            for (x = - xRange - 1; x <= xRange + 1; ++x) {
                for (y =  yRange; y >= - 1; --y) {
                    for (z = - zRange - 1; z <= zRange + 1; ++z) {
                        BlockPos blockPos = pos.add(x, y, z);
                        if (x != - xRange - 1 && y != - 1 && z != - zRange - 1 &&
                                x != xRange + 1 && y != yRange + 1 && z != zRange + 1) {
                            if (worldIn.getBlockState(blockPos).getBlock() != Blocks.CHEST) {
                                worldIn.setBlockToAir(blockPos);
                            }
                        } else if (y >= 0 && !worldIn.getBlockState(blockPos.down()).getMaterial().isSolid()) {
                            worldIn.setBlockToAir(blockPos);
                        } else if (worldIn.getBlockState(blockPos).getMaterial().isSolid() && worldIn.getBlockState(blockPos).getBlock() != Blocks.CHEST) {
                            if (y == - 1 && rand.nextInt(4) != 0) {

                                this.setRandomBlock(worldIn, blockPos);
                            } else {

                                this.setBaseBlock(worldIn, blockPos);
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 3; ++j) {
                    int chestX = pos.getX() + rand.nextInt(xRange * 2 + 1) - xRange;
                    int chestY = pos.getY();
                    int chestZ = pos.getZ() + rand.nextInt(zRange * 2 + 1) - zRange;
                    BlockPos chestPos = new BlockPos(chestX, chestY, chestZ);

                    if (worldIn.isAirBlock(chestPos)) {
                        int j3 = 0;

                        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                            if (worldIn.getBlockState(chestPos.offset(enumfacing)).getMaterial().isSolid()) {
                                ++j3;
                            }
                        }

                        if (j3 == 1) {
                            worldIn.setBlockState(chestPos, Blocks.CHEST.correctFacing(worldIn, chestPos, Blocks.CHEST.getDefaultState()), 2);
                            TileEntity tileEntityChest = worldIn.getTileEntity(chestPos);

                            if (tileEntityChest instanceof TileEntityChest) {
                                ((TileEntityChest) tileEntityChest).setLootTable(getLootTable(rand), rand.nextLong());
                            }

                            break;
                        }
                    }
                }
            }

            worldIn.setBlockState(pos, Blocks.MOB_SPAWNER.getDefaultState(), 2);
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntityId(this.pickSpawnMob(rand));
            } else {
                LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})"
                        , new Object[]{
                                Integer.valueOf(pos.getX())
                                , Integer.valueOf(pos.getY())
                                , Integer.valueOf(pos.getZ())});
            }

            return true;
        } else {
            return false;
        }

    }

    abstract protected void setBaseBlock(World worldIn, BlockPos pos);

    abstract void setRandomBlock(World worldIn, BlockPos pos);

    protected ResourceLocation pickSpawnMob(Random rand) {
        return this.mobType == null ? this.pickSpawnMobAtRandomly(rand) : this.mobType;
    }

    protected ResourceLocation pickSpawnMobAtRandomly(Random rand) {
        return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(rand);
    }

    protected ResourceLocation getLootTable(Random rand) {
        return this.lootTables.get(rand.nextInt(this.lootTables.size()));
    }

}
