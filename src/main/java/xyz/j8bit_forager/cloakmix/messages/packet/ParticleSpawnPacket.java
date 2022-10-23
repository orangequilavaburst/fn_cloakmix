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
import xyz.j8bit_forager.cloakmix.messages.ClientPacketHandlers;

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

    public Vec3 getStart(){ return this.start; }
    public Vec3 getEnd(){ return this.end; }
    public ParticleOptions getParticleOptions() { return this.particleOptions; }

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

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        ClientPacketHandlers.handleParticleSpawnPacket(this);
    }

}
