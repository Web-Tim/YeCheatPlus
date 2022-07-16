package me.tim.checks.impl.movement;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import me.tim.YeCheatPlus;
import me.tim.checks.Check;
import me.tim.utils.PlayerData;

public class NoFall extends Check {
    public NoFall() {
        super("NoFall", 15, true);
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;

        if (event.getPacketId() == PacketType.Play.Client.POSITION || event.getPacketId() == PacketType.Play.Client.POSITION_LOOK) {
            WrappedPacket wrappedPacket = new WrappedPacket(event.getNMSPacket());
            PlayerData data = YeCheatPlus.getInstance().playerData;

            if (data.airTicks >= 4 && (data.realGround != data.clientGround))
            {
                this.fail(event.getPlayer());
            }
        }
    }
}
