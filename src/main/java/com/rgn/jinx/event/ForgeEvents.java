package com.rgn.jinx.event;

import akka.japi.Pair;
import com.google.common.collect.Lists;
import com.rgn.jinx.init.JinxMessages;
import com.rgn.jinx.item.ItemElvenBow;
import com.rgn.jinx.item.ItemQuiver;
import com.rgn.jinx.network.EquipArrowInfoMessage;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMLeaf;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ForgeEvents {

    @SubscribeEvent
    public void changeEquipArrow(PlayerInteractEvent.LeftClickEmpty event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack mainHandItemStack = event.getItemStack();
        ItemStack offHandItemStack = player.getHeldItemOffhand();

        if (mainHandItemStack == null && offHandItemStack == null) {
            return ;
        }

        if (mainHandItemStack != null && mainHandItemStack.getItem() instanceof ItemArrow) {
            // if main hand item is arrow, end it.
            return ;
        }

        if (hasBow(mainHandItemStack) || hasBow(offHandItemStack)) {

            ItemStack equippedBow = getEquippedBow(player);

            if (player.isSneaking() && event.getSide().isClient()) {
                NBTTagCompound tag = equippedBow.getTagCompound();
                ItemElvenBow bow = (ItemElvenBow) equippedBow.getItem();
                List<Pair<ItemStack, Integer>> ammoList = bow.createAmmoList(player);

                if (ammoList != null) {

                    int ammoIndex = bow.readNBTTagCompoundFromItemStack(equippedBow).first();
                    int slotIndex = bow.readNBTTagCompoundFromItemStack(equippedBow).second();

                    ammoIndex++;
                    ammoIndex %= ammoList.size();

                    bow.informEquipArrow(player, ammoList.get(ammoIndex).first());
                    bow.writeItemStackToNBT(equippedBow, ammoIndex, slotIndex);

                    // sync client-server itemstack
                    JinxMessages.networkWrapper.sendToServer(new EquipArrowInfoMessage(ammoIndex, slotIndex));

                }
            }
        }

    }

    protected boolean hasBow(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() instanceof ItemElvenBow;
    }

    protected ItemStack getEquippedBow(EntityPlayer player) {

        ItemStack mainHandItemStack = player.getHeldItemMainhand();
        ItemStack offHandItemStack = player.getHeldItemOffhand();

        return hasBow(mainHandItemStack) ? mainHandItemStack : offHandItemStack;

    }


    @SubscribeEvent
    public void drawEquippedArrowIcon(RenderGameOverlayEvent.Post event) {

        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return ;
        }

        EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
        ItemStack bow = null;
        ItemStack arrow = null;

        if (player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ItemElvenBow) {
            bow = player.getHeldItemMainhand();
        } else if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() instanceof  ItemElvenBow) {
            bow = player.getHeldItemOffhand();
        } else {
            return ;
        }



        int ammoIndex = ((ItemElvenBow) bow.getItem()).readNBTTagCompoundFromItemStack(bow).first();

        int x = 16;
        int y = 16;


        List<Pair<ItemStack, Integer>> arrowList = ((ItemElvenBow) bow.getItem()).createAmmoList(player);
        arrow = ((ItemElvenBow) bow.getItem()).getEquipAmmo(bow, arrowList);

        String s;
        if (arrow.getItem() instanceof ItemQuiver) {
            s = String.valueOf(((ItemQuiver) arrow.getItem()).getArrowStackSize(arrow));
        } else {
            s = String.valueOf(arrow.stackSize);
        }

        RenderItem renderItem = FMLClientHandler.instance().getClient().getRenderItem();
        TextureManager textureManager = FMLClientHandler.instance().getClient().getTextureManager();

        IBakedModel iBakedModel = renderItem.getItemModelWithOverrides(arrow, null, null);

        GlStateManager.pushMatrix();
        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        GlStateManager.translate((float)x, (float)y, 100.0F);
        GlStateManager.translate(8.0F, 8.0F, 0.0F);
        GlStateManager.scale(1.0F, -1.0F, 1.0F);
        GlStateManager.scale(16.0F, 16.0F, 16.0F);

        GlStateManager.disableLighting();

        iBakedModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(iBakedModel, ItemCameraTransforms.TransformType.GUI, false);
        renderItem.renderItem(arrow, iBakedModel);

        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
//        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();

//        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
//        GlStateManager.disableBlend();
        FontRenderer fontRenderer = FMLClientHandler.instance().getClient().fontRendererObj;
        fontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - fontRenderer.getStringWidth(s)), (float)(y + 6 + 3), 16777215);
//        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        // Fixes opaque cooldown overlay a bit lower
        // TODO: check if enabled blending still screws things up down the line.
//        GlStateManager.enableBlend();

    }
//
//    protected void renderItemModelIntoGUI(ItemStack stack, int x, int y, IBakedModel bakedmodel)
//    {
//        TextureManager textureManager = FMLClientHandler.instance().getClient().getTextureManager();
//        GlStateManager.pushMatrix();
//        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//        textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
//        GlStateManager.enableRescaleNormal();
//        GlStateManager.enableAlpha();
//        GlStateManager.alphaFunc(516, 0.1F);
//        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        //this.setupGuiTransform(x, y, bakedmodel.isGui3d());
//        bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.GUI, false);
//        FMLClientHandler.instance().getClient().getRenderItem().renderItem(stack, bakedmodel);
//        GlStateManager.disableAlpha();
//        GlStateManager.disableRescaleNormal();
//        GlStateManager.disableLighting();
//        GlStateManager.popMatrix();
//        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
//        textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
//    }
}
