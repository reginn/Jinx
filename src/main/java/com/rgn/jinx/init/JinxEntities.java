package com.rgn.jinx.init;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.client.render.RenderElvenArrow;
import com.rgn.jinx.entity.projectile.EntityElvenArrow;
import com.rgn.jinx.entity.projectile.EntityTorchArrow;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class JinxEntities {

    @SubscribeEvent
    public void register(RegistryEvent.Register<EntityEntry> event) {
        IForgeRegistry<EntityEntry> registry = event.getRegistry();

        EntityRegistry.registerModEntity(new ResourceLocation(Jinx.MODID, "entityTorchArrow") ,EntityTorchArrow.class, "TorchArrow", 0, Jinx.instance, 250, 1, true);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void register(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityTorchArrow.class, new IRenderFactory<EntityElvenArrow>() {
            @Override
            public Render<? super EntityElvenArrow> createRenderFor(RenderManager manager) {
                return new RenderElvenArrow(manager);
            }
        });
    }

}
