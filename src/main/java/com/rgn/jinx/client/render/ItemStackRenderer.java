package com.rgn.jinx.client.render;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;

import javax.annotation.Nonnull;

public class ItemStackRenderer {

    private static ItemStackRenderer INSTANCE;
    private static RenderItem renderItem;
    private static TextureManager textureManager;
    private static FontRenderer fontRenderer;

    private ItemStackRenderer() {}

    public static ItemStackRenderer instance() {

        if (INSTANCE == null) {
            INSTANCE = new ItemStackRenderer();
            renderItem = FMLClientHandler.instance().getClient().getRenderItem();
            textureManager = FMLClientHandler.instance().getClient().getTextureManager();
            fontRenderer = FMLClientHandler.instance().getClient().fontRenderer;
        }
        return INSTANCE;
    }

    public void renderItemStackIn2D(@Nonnull ItemStack itemStack, int xPosition, int yPosition) {
        IBakedModel iBakedModel = renderItem.getItemModelWithOverrides(itemStack, null, null);

        GlStateManager.pushMatrix();
        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        GlStateManager.translate((float) xPosition, (float) yPosition, 100.0F);
        GlStateManager.translate(8.0F, 8.0F, 0.0F);
        GlStateManager.scale(1.0F, -1.0F, 1.0F);
        GlStateManager.scale(16.0F, 16.0F, 16.0F);

        GlStateManager.disableLighting();

        iBakedModel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(iBakedModel, ItemCameraTransforms.TransformType.GUI, false);
        renderItem.renderItem(itemStack, iBakedModel);

        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        textureManager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
    }

    public void renderItemStackSize(@Nonnull ItemStack itemStack, int xPosition, int yPosition) {
        this.renderStringOnItemStack(String.valueOf(itemStack.getCount()), itemStack, xPosition, yPosition);
    }

    public void renderStringOnItemStack(@Nonnull String string, @Nonnull ItemStack itemStack, int xPosition, int yPosition) {
        GlStateManager.disableDepth();
        fontRenderer.drawStringWithShadow(string, (float) (xPosition + 19 - 2 - fontRenderer.getStringWidth(string)), (float) (yPosition + 6 + 3), 16777215);
        GlStateManager.enableDepth();
    }




}
