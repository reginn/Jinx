package com.rgn.jinx.item;

import com.google.common.collect.Sets;
import com.rgn.jinx.Jinx;
import com.rgn.jinx.init.JinxConstants;
import com.rgn.jinx.init.JinxTranslations;
import com.rgn.jinx.inventory.InventorySeedBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;


public class ItemSeedBag extends Item {

    public ItemSeedBag() {
        super();
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack seedBag, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.isSneaking()) {
            return super.onItemUse(seedBag, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        if (worldIn.getBlockState(pos).getBlock() == Blocks.FARMLAND || worldIn.getBlockState(pos).getBlock() == Blocks.SOUL_SAND) {
            if (seedBag.hasTagCompound()) {
                IInventory inventorySeedBag = new InventorySeedBag(seedBag);
                for (int i = 0; i < inventorySeedBag.getSizeInventory(); i++) {
                    ItemStack seed = inventorySeedBag.getStackInSlot(i);
                    if (seed != null) {
                        this.tryPlaceSeedOnAroundBlock(seed, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
                        inventorySeedBag.setInventorySlotContents(i, seed.stackSize != 0 ? inventorySeedBag.decrStackSize(i, seed.stackSize): null);
                        inventorySeedBag.closeInventory(playerIn);
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }

        return EnumActionResult.FAIL;
    }

    private void tryPlaceSeedOnAroundBlock(ItemStack seed, EntityPlayer player, World world, BlockPos blockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        int playerDir = MathHelper.floor_double((double)((player.rotationYaw * 4F) / 360F) + 0.5D) & 0x03;
        int[] playerFacing = new int[] {2, 5, 3, 4};

        Set<BlockPos> targetsSet = this.getPositions(EnumFacing.getFront(playerFacing[playerDir]), blockPos);

        for (BlockPos target : targetsSet) {
            if (!world.isRemote && seed.stackSize > 0) {
                seed.onItemUse(player, world, target, hand, facing, hitX, hitY, hitZ);
            }
        }
    }

    private Set<BlockPos> getPositions(EnumFacing facing, BlockPos blockPos) {
        Set<BlockPos> targets = Sets.newLinkedHashSet();

        int[] upper = new int[]{-2, -1, 0,  1,  2};
        int[] lower = new int[]{ 2,  1, 0, -1, -2};

        int[] dx = upper;
        int[] dz = upper;

        switch (facing) {
            case EAST:
                dx = lower;
                dz = upper;
                break;

            case WEST:
                dx = upper;
                dz = lower;
                break;
            case SOUTH:
                dx = upper;
                dz = upper;
                break;
            case NORTH:
                dx = lower;
                dz = lower;
                break;
        }

        for (int i : dx) {
            for (int j : dz) {
                int _x = blockPos.getX() + i;
                int _z = blockPos.getZ() + j;
                if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                    _x = blockPos.getX() + j;
                    _z = blockPos.getZ() + i;
                }
                targets.add((new BlockPos(_x, blockPos.getY(), _z)));
            }
        }

        return targets;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        if (itemStackIn != null && itemStackIn.getItem() instanceof ItemSeedBag &&
                playerIn != null && playerIn.isSneaking()) {

            playerIn.openGui(Jinx.instance, JinxConstants.GUI_ID_SEED_BAG, worldIn, 0, 0, 0);

        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }


    @Override
    public void addInformation(ItemStack seedBag, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        TextComponentTranslation seedType = new TextComponentTranslation(JinxTranslations.SEED_TYPE_IN_BAG);
        TextComponentTranslation stackSize = new TextComponentTranslation(JinxTranslations.SEED_STACKSIZE_IN_BAG);
        TextComponentTranslation empty = new TextComponentTranslation(JinxTranslations.EMPTY_SEED_BAG);

        ItemStack seed = this.getSeedFromBag(seedBag);
        tooltip.add(seedType.getFormattedText() + " : " + (seed != null ? seed.getDisplayName() : empty.getFormattedText()));
        tooltip.add(stackSize.getFormattedText() + " : " + (seed != null ? this.getSeedStackSize(seedBag) : "0"));

    }

    public int getSeedStackSize(ItemStack seedBag) {
        IInventory inventorySeedBag = new InventorySeedBag(seedBag);
        ItemStack seed;
        int stackSize = 0;

        for (int i = 0; i < inventorySeedBag.getSizeInventory(); i++) {
            seed = inventorySeedBag.getStackInSlot(i);
            if (seed != null) {
                stackSize += seed.stackSize;
            }
        }
        return stackSize;
    }

    @Override
    public String getItemStackDisplayName(ItemStack seedBag) {

        ItemStack seed = getSeedFromBag(seedBag);
        String seedType = null;

        if (seed != null) {
            seedType = "(" + seed.getDisplayName() + ")";
        }

        return super.getItemStackDisplayName(seedBag) + seedType;
    }

    @Nullable
    protected ItemStack getSeedFromBag(ItemStack seedBag) {

        IInventory inventorySeedBag = new InventorySeedBag(seedBag);
        ItemStack seed = null;

        for (int i = 0; i < inventorySeedBag.getSizeInventory(); i++) {
            seed = inventorySeedBag.getStackInSlot(i);
            if (seed != null) {
                break;
            }
        }
        return seed;
    }
}
