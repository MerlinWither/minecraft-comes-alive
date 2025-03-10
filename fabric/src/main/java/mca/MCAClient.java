package mca;

import mca.block.BlockEntityTypesMCA;
import mca.block.BlocksMCA;
import mca.client.gui.FabricMCAScreens;
import mca.client.particle.InteractionParticle;
import mca.client.render.GrimReaperRenderer;
import mca.client.render.TombstoneBlockEntityRenderer;
import mca.client.render.VillagerEntityMCARenderer;
import mca.client.render.ZombieVillagerEntityMCARenderer;
import mca.entity.EntitiesMCA;
import mca.item.BabyItem;
import mca.item.ItemsMCA;
import mca.resources.FabricColorPaletteLoader;
import mca.resources.FabricSupportersLoader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.entity.ZombieVillagerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public final class MCAClient extends ClientProxyAbstractImpl implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        if (Config.getInstance().useSquidwardModels) {
            EntityRendererRegistry.register(EntitiesMCA.MALE_VILLAGER.get(), VillagerEntityRenderer::new);
            EntityRendererRegistry.register(EntitiesMCA.FEMALE_VILLAGER.get(), VillagerEntityRenderer::new);

            EntityRendererRegistry.register(EntitiesMCA.MALE_ZOMBIE_VILLAGER.get(), ZombieVillagerEntityRenderer::new);
            EntityRendererRegistry.register(EntitiesMCA.FEMALE_ZOMBIE_VILLAGER.get(), ZombieVillagerEntityRenderer::new);
        } else {
            EntityRendererRegistry.register(EntitiesMCA.MALE_VILLAGER.get(), VillagerEntityMCARenderer::new);
            EntityRendererRegistry.register(EntitiesMCA.FEMALE_VILLAGER.get(), VillagerEntityMCARenderer::new);

            EntityRendererRegistry.register(EntitiesMCA.MALE_ZOMBIE_VILLAGER.get(), ZombieVillagerEntityMCARenderer::new);
            EntityRendererRegistry.register(EntitiesMCA.FEMALE_ZOMBIE_VILLAGER.get(), ZombieVillagerEntityMCARenderer::new);
        }

        EntityRendererRegistry.register(EntitiesMCA.GRIM_REAPER.get(), GrimReaperRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ParticleTypesMCA.NEG_INTERACTION.get(), InteractionParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleTypesMCA.POS_INTERACTION.get(), InteractionParticle.Factory::new);

        BlockEntityRendererRegistry.register(BlockEntityTypesMCA.TOMBSTONE.get(), TombstoneBlockEntityRenderer::new);

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new FabricMCAScreens());
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new FabricColorPaletteLoader());
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new FabricSupportersLoader());

        FabricModelPredicateProviderRegistry.register(ItemsMCA.BABY_BOY.get(), new Identifier("invalidated"), (stack, world, entity, i) -> {
            return BabyItem.hasBeenInvalidated(stack) ? 1 : 0;
        });
        FabricModelPredicateProviderRegistry.register(ItemsMCA.BABY_GIRL.get(), new Identifier("invalidated"), (stack, world, entity, i) -> {
            return BabyItem.hasBeenInvalidated(stack) ? 1 : 0;
        });

        BlockRenderLayerMap.INSTANCE.putBlock(BlocksMCA.INFERNAL_FLAME.get(), RenderLayer.getCutout());
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return MinecraftClient.getInstance().player;
    }
}
