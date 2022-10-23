package xyz.j8bit_forager.cloakmix.messages;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import xyz.j8bit_forager.cloakmix.CloakMix;
import xyz.j8bit_forager.cloakmix.messages.packet.ParticleSpawnPacket;

public class ModMessages {

    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id(){

        return packetID++;

    }

    public static void register(){

        SimpleChannel network = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(CloakMix.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = network;

        network.messageBuilder(ParticleSpawnPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ParticleSpawnPacket::new)
                //.decoder(ParticleSpawnPacket::fromBytes)
                .encoder(ParticleSpawnPacket::toBytes)
                .consumerMainThread(ParticleSpawnPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer serverPlayer){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }

}
