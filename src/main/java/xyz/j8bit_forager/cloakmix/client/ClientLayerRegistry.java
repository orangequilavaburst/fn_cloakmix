package xyz.j8bit_forager.cloakmix.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.client.render.AlteredSightLayer;
import xyz.j8bit_forager.cloakmix.client.render.AnonymityLayer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = CloakMix.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientLayerRegistry {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(
                ForgeRegistries.ENTITY_TYPES.getValues().stream()
                        .filter(DefaultAttributes::hasSupplier)
                        .map(entityType -> (EntityType<? extends LivingEntity>) entityType)
                        .collect(Collectors.toList()));
        entityTypes.forEach((entityType -> {
            addASLayerIfApplicable(entityType, event);
        }));
        for (String skinType : event.getSkins()){
            event.getSkin(skinType).addLayer(new AlteredSightLayer(event.getSkin(skinType)));
        }

    }

    private static void addASLayerIfApplicable(EntityType<? extends LivingEntity> entityType, EntityRenderersEvent.AddLayers event) {
        LivingEntityRenderer renderer = null;
        if(entityType != EntityType.ENDER_DRAGON){
            try{
                renderer = event.getRenderer(entityType);
            }catch (Exception e){
                CloakMix.LOGGER.warn("Could not apply altered sight color layer to " + entityType.getDescription() + ", has custom renderer that is not LivingEntityRenderer.");
            }
            if(renderer != null){
                CloakMix.LOGGER.info("Added altered sight layer to " + entityType.getDescription());
                renderer.addLayer(new AlteredSightLayer(renderer));
            }
        }
    }

    private static <T extends LivingEntity, R extends LivingEntityRenderer<T, M>, M extends EntityModel<T>>
    void addLayerToRenderer(EntityRenderersEvent.AddLayers event, EntityType<T> entityType, Function<R, ? extends RenderLayer<T,M>> factory)
    {
        R renderer = event.getRenderer(entityType);
        if (renderer != null){
            renderer.addLayer(factory.apply(renderer));
            CloakMix.LOGGER.info("Added" + factory.toString() + " layer to " + entityType.getDescription());
        }
        else{
            CloakMix.LOGGER.info("Could not add" + factory.toString() + " layer to " + entityType.getDescription());
        }
    }

}