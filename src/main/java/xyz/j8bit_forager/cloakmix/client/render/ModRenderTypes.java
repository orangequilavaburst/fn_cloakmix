package xyz.j8bit_forager.cloakmix.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.j8bit_forager.cloakmix.CloakMix;

import java.io.IOException;
import java.util.function.Function;

public class ModRenderTypes {

    // Accessor functon, ensures that you don't use the raw methods below unintentionally.
    public static RenderType alteredSight(ResourceLocation texture)
    {
        return CustomRenderTypes.ALTERED_SIGHT.apply(texture);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CloakMix.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientEvents
    {
        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException
        {
            // Adds a shader to the list, the callback runs when loading is complete.
            event.registerShader(new ShaderInstance(event.getResourceManager(), new ResourceLocation(CloakMix.MOD_ID, "rendertype_altered_sight"), DefaultVertexFormat.POSITION), shaderInstance -> {
                CustomRenderTypes.alteredSightShader = shaderInstance;
            });
        }
    }

    // Keep private because this stuff isn't meant to be public
    private static class CustomRenderTypes extends RenderType
    {
        // Holds the object loaded via RegisterShadersEvent
        private static ShaderInstance alteredSightShader;

        // Shader state for use in the render type, the supplier ensures it updates automatically with resource reloads
        private static final ShaderStateShard ALTERED_SIGHT_SHADER = new ShaderStateShard(() -> alteredSightShader);

        // Dummy constructor needed to make java happy
        private CustomRenderTypes(String s, VertexFormat v, VertexFormat.Mode m, int i, boolean b, boolean b2, Runnable r, Runnable r2)
        {
            super(s, v, m, i, b, b2, r, r2);
            throw new IllegalStateException("This class is not meant to be constructed!");
        }

        // The memoize caches the output value for each input, meaning the expensive registration process doesn't have to rerun
        public static Function<ResourceLocation, RenderType> ALTERED_SIGHT = Util.memoize(CustomRenderTypes::alteredSight);

        // Defines the RenderType. Make sure the name is unique by including your MODID in the name.
        private static RenderType alteredSight(ResourceLocation locationIn)
        {
            RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder()
                    .setShaderState(ALTERED_SIGHT_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(locationIn, false, false))
                    .setTextureState(new RenderStateShard.TextureStateShard(locationIn, false, false))
                    .createCompositeState(true);
            return create("cloakmix_altered_sight", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 256, true, false, rendertype$state);
        }
    }

}
