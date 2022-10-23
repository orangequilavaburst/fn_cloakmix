package xyz.j8bit_forager.cloakmix.messages;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.j8bit_forager.cloakmix.messages.packet.ParticleSpawnPacket;

public class ClientPacketHandlers {

    public static void handleParticleSpawnPacket(ParticleSpawnPacket packet){

        // here we are in the server!
        // here we are in the server and it's bright!

        Player player = Minecraft.getInstance().player;
        Level level = Minecraft.getInstance().level;

        double distance = packet.getStart().distanceTo(packet.getEnd());
        if (distance > 0) {
            for (float i = 1; i < distance - 1.0f; i += 0.5f) {
                float t = i / (float) distance;
                level.addParticle(packet.getParticleOptions(), true,
                        Mth.lerp(t, packet.getStart().x(), packet.getEnd().x()),
                        Mth.lerp(t, packet.getStart().y(), packet.getEnd().y()),
                        Mth.lerp(t, packet.getStart().z(), packet.getEnd().z()),
                        0.0f, 0.0f, 0.0f);
            }
        }

    }
}
