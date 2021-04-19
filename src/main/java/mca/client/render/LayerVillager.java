package mca.client.render;

import com.google.common.collect.Maps;
import mca.client.model.ModelVillagerMCA;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@SideOnly(Side.CLIENT)
public abstract class LayerVillager implements LayerRenderer<EntityLivingBase> {
    protected ModelBase model;

    private final RenderLivingBase<?> renderer;
    protected static final Map<String, ResourceLocation> textureRes = Maps.newHashMap();

    public LayerVillager(RenderLivingBase<?> rendererIn, float offset) {
        this.renderer = rendererIn;
        this.model = new ModelVillagerMCA(offset, true);
    }

    abstract boolean hasOverlay(EntityLivingBase entity);

    abstract String getClothing(EntityLivingBase entity);

    float[] getColor(EntityLivingBase entity) {
        return new float[] {1.0f, 1.0f, 1.0f};
    }

    @ParametersAreNonnullByDefault
    public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        for (int overlay = 0; overlay <= 1; overlay++) {
            if (overlay == 0 || hasOverlay(entity)) {
                ResourceLocation res = this.getClothingResource(entity, overlay == 1);
                this.renderer.bindTexture(res);

                //color the non overlay
                if (overlay == 0) {
                    float[] color = getColor(entity);
                    GlStateManager.color(color[0], color[1], color[2], 1.0f);
                } else {
                    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                }

                model.setModelAttributes(this.renderer.getMainModel());
                model.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    private ResourceLocation getClothingResource(EntityLivingBase entity, boolean overlay) {
        String s = getClothing(entity) + (overlay ? "_overlay" : "") + ".png";

        ResourceLocation resourcelocation = textureRes.get(s);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            textureRes.put(s, resourcelocation);
        }
        return resourcelocation;
    }
}