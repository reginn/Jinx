package com.rgn.jinx.client.gui;

import com.rgn.jinx.inventory.ContainerQuiver;
import com.rgn.jinx.item.EnumQuiverSize;
import com.rgn.jinx.item.ItemQuiver;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiQuiver extends GuiContainer {

    private final EntityPlayer player;
    private final EnumQuiverSize quiverSize;

    public GuiQuiver(World world, EntityPlayer player, ItemStack itemStack)
    {
        super(new ContainerQuiver(world, player, itemStack));
        this.player = player;

        this.quiverSize = ((ItemQuiver) itemStack.getItem()).getQuiverSize();
    }

    @Override
    public void onGuiClosed()
    {
        super.onGuiClosed();
        ((ContainerQuiver)this.inventorySlots).onContainerClosed(this.player);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString(((ContainerQuiver)this.inventorySlots).getInventoryQuiver().getDisplayName().getUnformattedText(), 60, 6, 0x404040);
        this.fontRendererObj.drawString(((ContainerQuiver)this.inventorySlots).getPlayerInventory().getDisplayName().getUnformattedText(), 8, (ySize - 96) + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(this.quiverSize.getGuiImageResourceLocation());
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}
