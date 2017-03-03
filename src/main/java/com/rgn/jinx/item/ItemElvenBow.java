package com.rgn.jinx.item;

import com.rgn.jinx.init.JinxItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

/**
 * Created by Reginn666 on 2017/03/01.
 */
public class ItemElvenBow extends ItemBow {
    protected double   baseDamage;
    protected float   velocityRatio;
    protected int     enchantability;
    protected boolean isCallEvent = true;
    protected float   chargeSpeedRatio = 1.0F;
    protected String  information;
    protected boolean rarity;

    protected EnumElvenBowType bowType;

    public ItemElvenBow(EnumElvenBowType type)
    {
        this.maxStackSize   = 1;
        this.baseDamage     = type.getBaseDamage();
        this.velocityRatio  = type.getVelocityRatio();
        this.enchantability = type.getEnchantability();
        this.bowType        = type;

        this.setMaxDamage(type.getDurability());

        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    ItemStack itemstack = entityIn.getActiveItemStack();
                    return itemstack != null && itemstack.getItem() instanceof ItemBow ? (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F : 0.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    @Override
    protected boolean isArrow(@Nullable ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof ItemElvenArrow;
    }


    protected ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isArrow(itemstack))
                {
                    return itemstack;
                }
            }

            return null;
        }
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack arrow = this.findAmmo(entityplayer);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, (EntityPlayer)entityLiving, i, arrow != null || flag);
            if (i < 0) return;

            if (arrow != null || flag)
            {
                if (arrow == null)
                {
                    arrow = new ItemStack(JinxItems.itemElvenArrow);
                }

                float f = getArrowVelocity(i);

                if ((double)f >= 0.1D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (arrow.getItem() instanceof ItemElvenArrow ? ((ItemElvenArrow)arrow.getItem()).isInfinite(arrow, stack, entityplayer) : false);

                    if (!worldIn.isRemote)
                    {
                        ItemElvenArrow itemArrow = (ItemElvenArrow) (arrow.getItem() instanceof ItemElvenArrow ? arrow.getItem() : JinxItems.itemElvenArrow);
                        EntityArrow entityarrow = itemArrow.createArrow(worldIn, arrow, entityplayer);
                        entityarrow.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * this.bowType.getVelocityRatio(), 1.0F);
                        entityarrow.setDamage(bowType.getBaseDamage());

                        if (f == 1.0F)
                        {
                            entityarrow.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);

                        if (j > 0)
                        {
                            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

                        if (k > 0)
                        {
                            entityarrow.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
                        {
                            entityarrow.setFire(100);
                        }

                        stack.damageItem(1, entityplayer);

                        if (flag1)
                        {
                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                        }

                        worldIn.spawnEntityInWorld(entityarrow);
                    }

                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1)
                    {
                        --arrow.stackSize;

                        if (arrow.stackSize == 0)
                        {
                            entityplayer.inventory.deleteStack(arrow);
                        }
                    }

                    entityplayer.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }

}
