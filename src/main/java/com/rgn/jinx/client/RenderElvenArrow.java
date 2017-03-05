package com.rgn.jinx.client;

import com.rgn.jinx.entity.projectile.EntityElvenArrow;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTippedArrow;
import net.minecraft.util.ResourceLocation;

public class RenderElvenArrow extends RenderArrow<EntityElvenArrow> {

    public RenderElvenArrow(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityElvenArrow entity) {
        return RenderTippedArrow.RES_ARROW;
    }
}
