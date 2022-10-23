package xyz.j8bit_forager.cloakmix.messages.packet;

import io.netty.buffer.ByteBufUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;
import xyz.j8bit_forager.cloakmix.CloakMix;

import java.util.function.Supplier;

public class ParticleSpawnPacket {


    private Vec3 start;
    private Vec3 end;
    private ParticleOptions particleOptions;
    private float maxDist;

    public ParticleSpawnPacket(){

        // :)
        start = new Vec3(0.0f, 0.0f, 0.0f);
        end = new Vec3(0.0f, 1.0f, 0.0f);
        particleOptions = ParticleTypes.HEART;
        maxDist = 0.0f;

    }

    public ParticleSpawnPacket(Vec3 _start, Vec3 _end, ParticleOptions _particleOptions, float _maxDist){

        start = _start;
        end = _end;
        particleOptions = _particleOptions;
        maxDist = _maxDist;

    }

    public ParticleSpawnPacket(FriendlyByteBuf buffer){

        // :D
        start = new Vec3(0.0f, 0.0f, 0.0f);
        end = new Vec3(0.0f, 1.0f, 0.0f);
        particleOptions = ParticleTypes.HEART;
        maxDist = 0.0f;

    }

    public void toBytes(FriendlyByteBuf buffer){

        // :3
        ByteBufUtil.writeMediumBE(buffer, start.hashCode());
        ByteBufUtil.writeMediumBE(buffer, end.hashCode());
        ByteBufUtil.writeAscii(buffer, particleOptions.writeToString());
        ByteBufUtil.writeMediumBE(buffer, Float.hashCode(maxDist));

    }

    public void fromBytes(FriendlyByteBuf buffer){

        start = Vec3.fromRGB24(ByteBufUtil.getBytes(buffer)[0]);
        end = Vec3.fromRGB24(ByteBufUtil.getBytes(buffer)[1]);
        particleOptions = ParticleTypes.FLAME; //??
        maxDist = Float.intBitsToFloat(ByteBufUtil.getBytes(buffer)[3]);

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){

        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            // here we are in the server!
            // here we are in the server and it's bright!

            ServerPlayer player = context.getSender();
            ServerLevel level = context.getSender().getLevel();

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

                    player.sendSystemMessage(Component.literal("Particle " + i + " spawned at: (" +
                            Mth.lerp(t, start.x(), end.x()) + ", " +
                            Mth.lerp(t, start.y(), end.y()) + ", " +
                            Mth.lerp(t, start.z(), end.z()) + ")"));
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
