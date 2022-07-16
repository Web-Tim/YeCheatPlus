package me.tim.checks.impl.combat.killaura;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import me.tim.checks.Check;
import me.tim.utils.Timer;

public class KillAuraB extends Check {
    private Timer lookTimer;

    public KillAuraB() {
        super("KillAura (B)", 20, true);
        this.lookTimer = new Timer();
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;

        if (event.getPacketId() == PacketType.Play.Client.LOOK || event.getPacketId() == PacketType.Play.Client.POSITION_LOOK || event.getPacketId() == PacketType.Play.Client.FLYING || event.getPacketId() == PacketType.Play.Client.POSITION) {
            this.lookTimer.reset();
        }else
            if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY && this.lookTimer.hasTimePassed(10))
        {
            this.fail(event.getPlayer());
        }
    }
}
