package me.tim.checks.impl.combat.killaura;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import me.tim.checks.Check;

public class KillAuraA extends Check {
    public KillAuraA() {
        super("KillAura (A)", 30, true);
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;

        if (event.getPacketId() == PacketType.Play.Client.LOOK || event.getPacketId() == PacketType.Play.Client.POSITION_LOOK) {
            WrappedPacket packet = new WrappedPacket(event.getNMSPacket());
            float pitch = packet.readFloat(1);

            if (pitch / 90 > 1 || pitch / 90 < -1)
                this.fail(event.getPlayer());
        }
    }
}
