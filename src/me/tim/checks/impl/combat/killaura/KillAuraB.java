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

        if (this.getPacketUtil().isRotationPacket(event.getPacketId()) || this.getPacketUtil().isPositionPacket(event.getPacketId()) || event.getPacketId() == PacketType.Play.Client.FLYING) {
            this.lookTimer.reset();
        }else if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY && this.lookTimer.hasTimePassed(10)) {
            this.fail(event.getPlayer());
        }
    }
}
