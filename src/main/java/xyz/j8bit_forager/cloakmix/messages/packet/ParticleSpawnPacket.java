package xyz.j8bit_forager.cloakmix.messages.packet;

import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.j8bit_forager.cloakmix.CloakMix;

import java.util.function.Supplier;

public class ParticleSpawnPacket {


    private Vec3 start;
    private Vec3 end;
    private ParticleOptions particleOptions;

    public ParticleSpawnPacket(Vec3 _start, Vec3 _end, ParticleOptions _particleOptions){

        start = _start;
        end = _end;
        particleOptions = _particleOptions;

    }

    public ParticleSpawnPacket(FriendlyByteBuf buffer){

        // :D
        fromBytes(buffer);

    }

    // thanks to Random on Discord
    public void toBytes(FriendlyByteBuf buffer){
        buffer.writeDouble(start.x);
        buffer.writeDouble(start.y);
        buffer.writeDouble(start.z);
        buffer.writeDouble(end.x);
        buffer.writeDouble(end.y);
        buffer.writeDouble(end.z);
        buffer.writeRegistryId(ForgeRegistries.PARTICLE_TYPES, particleOptions.getType());
        particleOptions.writeToNetwork(buffer);
    }

    public <T extends ParticleOptions> T readParticleOptions(FriendlyByteBuf buffer) {
        ParticleType<T> particleType = buffer.readRegistryIdSafe(ParticleType.class);
        return particleType.getDeserializer().fromNetwork(particleType, buffer);
    }

    public void fromBytes(FriendlyByteBuf buffer){
        start = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        end = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        particleOptions = readParticleOptions(buffer);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            // here we are in the server!
            // here we are in the server and it's bright!

            Player player = Minecraft.getInstance().player;
            Level level = Minecraft.getInstance().level;

            //level.sendParticles(ParticleTypes.HEART, player.getX(), player.getY(), player.getZ(),1, 0.0, 0.0, 0.0, 0.0);

            double distance = start.distanceTo(end);
            //CloakMix.LOGGER.info("Distance: " + distance);
            //player.sendSystemMessage(Component.literal("Distance: " + distance));
            if (distance > 0) {
                for (float i = 1; i < distance - 1.0f; i += 0.5f) {
                    float t = i / (float) distance;
                    level.addParticle(particleOptions, true,
                            Mth.lerp(t, start.x(), end.x()),
                            Mth.lerp(t, start.y(), end.y()),
                            Mth.lerp(t, start.z(), end.z()),
                            0.0f, 0.0f, 0.0f);

                    /*player.sendSystemMessage(Component.literal("Particle " + i + " spawned at: (" +
                            Mth.lerp(t, start.x(), end.x()) + ", " +
                            Mth.lerp(t, start.y(), end.y()) + ", " +
                            Mth.lerp(t, start.z(), end.z()) + ")"));*/
                }
            }

        });
        return true;

    }

    /*public boolean handle(Supplier<NetworkEvent.Context> supplier, Vec3 start, Vec3 end, float maxDist, ParticleOptions particleOptions){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            // here we are in the server!
            // here we are in the server and it's bright!
            ServerPlayer player = context.getSender();
            ServerLevel level = context.getSender().getLevel();

            double distance = start.distanceTo(end);
            //CloakMix.LOGGER.info("Distance: " + distance);
            if (distance < maxDist) {
                for (float i = 1; i < distance - 1.0f; i += 0.5f) {
                    float t = i / (float) distance;
                    level.addParticle(particleOptions, true,
                            Mth.lerp(t, start.x(), end.x()),
                            Mth.lerp(t, start.y(), end.y()),
                            Mth.lerp(t, start.z(), end.z()),
                            0.0f, 0.0f, 0.0f);
                }
            }

        });
        return true;

    }*/

}
