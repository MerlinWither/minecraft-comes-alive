package mca;

import dev.architectury.platform.forge.EventBuses;
import mca.advancement.criterion.CriterionMCA;
import mca.block.BlocksMCA;
import mca.cobalt.network.NetworkHandlerImpl;
import mca.entity.EntitiesMCA;
import mca.entity.interaction.gifts.GiftLoader;
import mca.item.ItemsMCA;
import mca.network.MessagesMCA;
import mca.resources.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(mca.MCA.MOD_ID)
@Mod.EventBusSubscriber(modid = mca.MCA.MOD_ID, bus = Bus.MOD)
public final class MCAForge {
    public MCAForge() {
        EventBuses.registerModEventBus(MCA.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        new NetworkHandlerImpl();
        MinecraftForge.EVENT_BUS.addListener(this::onAddReloadListener);

        BlocksMCA.bootstrap();
        ItemsMCA.bootstrap();
        SoundsMCA.bootstrap();
        ParticleTypesMCA.bootstrap();
        EntitiesMCA.bootstrap();
        MessagesMCA.bootstrap();
        CriterionMCA.bootstrap();
    }

    private void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new ApiReloadListener());
        event.addListener(new ClothingList());
        event.addListener(new HairList());
        event.addListener(new GiftLoader());
        event.addListener(new Dialogues());
        event.addListener(new Tasks());
    }

//    @SubscribeEvent
//    public static void onCreateEntityAttributes(EntityAttributeCreationEvent event) {
//        EntitiesMCA.bootstrapAttributes();
////        RegistrationImpl.ENTITY_ATTRIBUTES.forEach((type, attributes) -> {
////            event.put(type, attributes.get().build());
////        });
//    }
}
