package com.rgn.jinx.entity;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.client.RenderElvenArrow;
import com.rgn.jinx.entity.projectile.EntityElvenArrow;
import com.rgn.jinx.entity.projectile.EntityTorchArrow;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class JinxEntities {

    public static void init(FMLPreInitializationEvent event) {

        EntityRegistry.registerModEntity(EntityTorchArrow.class, "TorchArrow", 0, Jinx.instance, 250, 1, true);


        if (event.getSide().isClient()) {
            RenderingRegistry.registerEntityRenderingHandler(EntityTorchArrow.class, new IRenderFactory<EntityElvenArrow>() {
                @Override
                public Render<? super EntityElvenArrow> createRenderFor(RenderManager manager) {
                    return new RenderElvenArrow(manager);
                }
            });
        }

    }

}
