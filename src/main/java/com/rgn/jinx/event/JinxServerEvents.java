package com.rgn.jinx.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class JinxServerEvents {

    @SubscribeEvent
    public void dropMobSpawner(BlockEvent.BreakEvent event) {

        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState blockState = event.getState();

        if (!world.isRemote) {

            if (blockState.getBlock() == Blocks.MOB_SPAWNER) {
                ItemStack mobSpawner = new ItemStack(Blocks.MOB_SPAWNER, 1);
                EntityItem entityItem = new EntityItem(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), mobSpawner);
                entityItem.setEntityItemStack(mobSpawner);

                world.spawnEntity(entityItem);
            }

        }

    }
}
