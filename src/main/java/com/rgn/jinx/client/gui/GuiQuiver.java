package com.rgn.jinx.client.gui;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.inventory.ContainerQuiver;
import com.rgn.jinx.item.EnumQuiverSize;
import com.rgn.jinx.item.ItemQuiver;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiQuiver extends GuiContainer {

    protected EntityPlayer player;
    protected static final ResourceLocation GUI_SMALL_QUIVER_RESOURCE_LOCATION = new ResourceLocation(Jinx.MODID,"textures/gui/container/quiver_small.png");
    protected static final ResourceLocation GUI_MIDDLE_QUIVER_RESOURCE_LOCATION = new ResourceLocation(Jinx.MODID,"textures/gui/container/quiver_middle.png");
    protected static final ResourceLocation GUI_LARGE_QUIVER_RESOURCE_LOCATION = new ResourceLocation(Jinx.MODID,"textures/gui/container/quiver_large.png");
    protected EnumQuiverSize quiverSize;
    protected ResourceLocation quiverResourceLocation;

    public GuiQuiver(World world, EntityPlayer player, ItemStack itemStack)
    {
        super(new ContainerQuiver(world, player, itemStack));
        this.player = player;

        quiverSize = ((ItemQuiver) itemStack.getItem()).getQuiverSize();

        switch(quiverSize) {
            case SMALL : this.quiverResourceLocation = GUI_SMALL_QUIVER_RESOURCE_LOCATION; break;
            case MIDDLE: this.quiverResourceLocation = GUI_MIDDLE_QUIVER_RESOURCE_LOCATION; break;
            case LARGE : this.quiverResourceLocation = GUI_LARGE_QUIVER_RESOURCE_LOCATION; break;
        }

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
        this.fontRendererObj.drawString(((ContainerQuiver)this.inventorySlots).getInventoryQuiver().getName(), 60, 6, 0x404040);
        this.fontRendererObj.drawString(((ContainerQuiver)this.inventorySlots).getPlayerInventory().getName(), 8, (ySize - 96) + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(this.quiverResourceLocation);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}
