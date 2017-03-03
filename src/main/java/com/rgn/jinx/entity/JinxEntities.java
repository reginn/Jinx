package com.rgn.jinx.entity;

import com.rgn.jinx.Jinx;
import com.rgn.jinx.client.RenderElvenArrow;
import com.rgn.jinx.entity.projectile.EntityElvenArrow;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTippedArrow;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * Created by Reginn666 on 2017/03/03.
 */
public class JinxEntities {

    public static void init(FMLPreInitializationEvent event) {


        EntityRegistry.registerModEntity(EntityElvenArrow.class, "ElvenArrow", 0, Jinx.instance, 250, 1, true);
        EntityRegistry.registerModEntity(EntityTorchArrow.class, "TorchArrow", 1, Jinx.instance, 250, 1, true);


        if (event.getSide().isClient()) {
            RenderingRegistry.registerEntityRenderingHandler(EntityElvenArrow.class, new IRenderFactory<EntityElvenArrow>() {
                @Override
                public Render<? super EntityElvenArrow> createRenderFor(RenderManager manager) {
                    return new RenderElvenArrow(manager);
                }
            });

            RenderingRegistry.registerEntityRenderingHandler(EntityTorchArrow.class, new IRenderFactory<EntityElvenArrow>() {
                @Override
                public Render<? super EntityElvenArrow> createRenderFor(RenderManager manager) {
                    return new RenderElvenArrow(manager);
                }
            });


        }

    }

}
