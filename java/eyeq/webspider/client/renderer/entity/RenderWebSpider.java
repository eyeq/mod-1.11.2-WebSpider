package eyeq.webspider.client.renderer.entity;

import eyeq.util.client.renderer.EntityRenderResourceLocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

import static eyeq.webspider.WebSpider.MOD_ID;

public class RenderWebSpider extends RenderSpider {
    protected static final ResourceLocation textures = new EntityRenderResourceLocation(MOD_ID, "web_spider");

    public RenderWebSpider(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySpider entity) {
        return textures;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entity, float partialTickTime) {
        GlStateManager.scale(0.8F, 1.1F, 1.3F);
    }
}
