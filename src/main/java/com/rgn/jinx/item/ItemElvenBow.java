package com.rgn.jinx.item;

import akka.japi.Pair;
import com.google.common.collect.Lists;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemElvenBow extends ItemBow {
    protected double baseDamage;
    protected float velocityRatio;
    protected int enchantability;
    protected boolean isCallEvent = true;
    protected float chargeSpeedRatio = 1.0F;
    protected String information;
    protected boolean rarity;

    protected EnumElvenBowType bowType;


    public ItemElvenBow(EnumElvenBowType type) {
        this.maxStackSize = 1;
        this.baseDamage = type.getBaseDamage();
        this.velocityRatio = type.getVelocityRatio();
        this.enchantability = type.getEnchantability();
        this.bowType = type;

        this.setMaxDamage(type.getDurability());

        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn == null) {
                    return 0.0F;
                } else {
                    ItemStack itemstack = entityIn.getActiveItemStack();
                    return itemstack != null && itemstack.getItem() instanceof ItemBow ? (float) (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }


    @Override
    public void onUpdate(ItemStack bow, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if (entityIn instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entityIn;
            List<Pair<ItemStack, Integer>> ammoList = createAmmoList(entityPlayer);
            NBTTagCompound tag = bow.getTagCompound();

            if (tag == null) {
                tag = new NBTTagCompound();
            }

            if (!tag.hasKey("ammoIndex")
                    || (tag.getInteger("ammoIndex") < 0 || tag.getInteger("ammoIndex") >= ammoList.size())
                    || ammoList.get(tag.getInteger("ammoIndex")).first() == null) {
                this.writeItemStackToNBT(bow, 0, ammoList.get(0).second().intValue());
            }
        }

        super.onUpdate(bow, worldIn, entityIn, itemSlot, isSelected);
    }

    public List<Pair<ItemStack, Integer>> createAmmoList(EntityPlayer player) {
        List<Pair<ItemStack, Integer>> ammoList = Lists.newArrayList();

        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND))) {
            ammoList.add(new Pair<ItemStack, Integer>(player.getHeldItem(EnumHand.OFF_HAND), -1));
        } else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND))) {
            ammoList.add(new Pair<ItemStack, Integer>(player.getHeldItem(EnumHand.MAIN_HAND), -2));
        } else {

            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isArrow(itemstack)) {
                    ammoList.add(new Pair<ItemStack, Integer>(itemstack, i));
                }
            }

        }

        return ammoList;
    }


    public ItemStack getEquipAmmo(ItemStack bow, List<Pair<ItemStack, Integer>> ammoList) {
        int ammoIndex = this.readNBTTagCompoundFromItemStack(bow).first();
        return ammoList.get(ammoIndex).first();
    }

    public void writeItemStackToNBT(ItemStack bow, int ammoIndex, int slotIndex) {

        NBTTagCompound tag = bow.getTagCompound();

        if (tag == null) {
            tag = new NBTTagCompound();
        }

        tag.setInteger("ammoIndex", ammoIndex);
        tag.setInteger("slotIndex", slotIndex);

        bow.setTagCompound(tag);

    }

    public Pair<Integer, Integer> readNBTTagCompoundFromItemStack(ItemStack bow) {

        int ammoIndex = 0;
        int slotIndex = 0;
        NBTTagCompound tag = bow.getTagCompound();

        if (tag == null) {
            tag = new NBTTagCompound();
        }

        if (tag.hasKey("ammoIndex") && tag.hasKey("slotIndex")) {
            ammoIndex = tag.getInteger("ammoIndex");
            slotIndex = tag.getInteger("slotIndex");

        }
        return new Pair<Integer, Integer>(ammoIndex, slotIndex);

    }


    @Override
    public void onPlayerStoppedUsing(ItemStack bow, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entityLiving;
            boolean isBowInfinity = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;

            List<Pair<ItemStack, Integer>> ammoList = this.createAmmoList(entityPlayer);

            ItemStack arrow = this.getEquipAmmo(bow, ammoList);


            int charge = this.getMaxItemUseDuration(bow) - timeLeft;
            charge = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bow, worldIn, (EntityPlayer) entityLiving, charge, arrow != null || isBowInfinity);
            if (charge < 0) return;

            if (arrow != null || isBowInfinity) {
                if (arrow == null) {
                    arrow = new ItemStack(Items.ARROW);
                }

                float arrowVelocity = getArrowVelocity(charge);

                if ((double) arrowVelocity >= 0.1D) {
                    boolean isArrowInfinity = entityPlayer.capabilities.isCreativeMode || (arrow.getItem() instanceof ItemArrow ? ((ItemArrow) arrow.getItem()).isInfinite(arrow, bow, entityPlayer) : false);

                    if (!worldIn.isRemote) {
                        ItemArrow itemArrow = (ItemArrow) (arrow.getItem() instanceof ItemArrow ? arrow.getItem() : Items.ARROW);
                        EntityArrow entityarrow = itemArrow.createArrow(worldIn, arrow, entityPlayer);
                        entityarrow.setAim(entityPlayer, entityPlayer.rotationPitch, entityPlayer.rotationYaw, 0.0F, arrowVelocity * this.bowType.getVelocityRatio(), 1.0F);
                        entityarrow.setDamage(bowType.getBaseDamage());

                        if (arrowVelocity == 1.0F) {
                            entityarrow.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow);

                        if (j > 0) {
                            entityarrow.setDamage(entityarrow.getDamage() + (double) j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow);

                        if (k > 0) {
                            entityarrow.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) > 0) {
                            entityarrow.setFire(100);
                        }

                        bow.damageItem(1, entityPlayer);

                        if (isArrowInfinity) {
                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                        }

                        entityPlayer.addChatMessage(new TextComponentString("Arror Type : " + itemArrow.getUnlocalizedName()));
                        worldIn.spawnEntityInWorld(entityarrow);
                    }

                    worldIn.playSound((EntityPlayer) null, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + arrowVelocity * 0.5F);

                    if (!isArrowInfinity) {
                        --arrow.stackSize;

                        if (arrow.stackSize == 0) {
                            entityPlayer.inventory.deleteStack(arrow);
                        }
                    }

                    entityPlayer.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }

    @Override
    public int getItemEnchantability() {
        return this.enchantability;
    }

    @Override
    public void addInformation(ItemStack bow, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

        List<Pair<ItemStack, Integer>> ammoList = createAmmoList(playerIn);
        NBTTagCompound tag = bow.getTagCompound();

        if (tag == null) {
            tag = new NBTTagCompound();
        }

        if (ammoList.size() != 0 && tag.hasKey("ammoIndex")) {

            TextComponentTranslation textComponentTranslation = new TextComponentTranslation("equip.arrow.name");

            tooltip.add(textComponentTranslation.getFormattedText() + " : " + ammoList.get(tag.getInteger("ammoIndex")).first().getDisplayName());

        }
    }

}