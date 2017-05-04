package com.rgn.jinx.client.gui;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.inventory.ContainerSeedBag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiSeedBag extends GuiContainer {

    private final EntityPlayer player;
    private final static ResourceLocation resourceLocation = new ResourceLocation(Jinx.MODID,"textures/gui/container/seedbag.png");

    public GuiSeedBag(World world, EntityPlayer player, ItemStack itemStack)
    {
        super(new ContainerSeedBag(world, player, itemStack));
        this.player = player;
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        ((ContainerSeedBag)this.inventorySlots).onContainerClosed(this.player);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(((ContainerSeedBag)this.inventorySlots).getInventorySeedBag().getDisplayName().getUnformattedText(), 60, 6, 0x404040);
        this.fontRendererObj.drawString(((ContainerSeedBag)this.inventorySlots).getPlayerInventory().getDisplayName().getUnformattedText(), 8, (ySize - 96) + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(resourceLocation);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}
