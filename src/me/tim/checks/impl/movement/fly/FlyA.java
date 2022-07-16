package me.tim.checks.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import me.tim.YeCheatPlus;
import me.tim.checks.Check;
import me.tim.utils.PlayerData;

public class FlyA extends Check {

    public FlyA() {
        super("Fly (A)", 20, true);
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;

        if (event.getPacketId() == PacketType.Play.Client.POSITION || event.getPacketId() == PacketType.Play.Client.POSITION_LOOK)
        {
            WrappedPacket wrappedPacket = new WrappedPacket(event.getNMSPacket());
            PlayerData data = YeCheatPlus.getInstance().playerData;
            if (data.airTicks >= 10 && !data.realGround && data.motionY > -0.4 && data.getDistanceToGround(event.getPlayer()) > 1.25)
            {
                this.fail(event.getPlayer());
            }
        }
    }
}
