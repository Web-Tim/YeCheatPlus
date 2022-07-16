package me.tim.checks.impl.combat;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import me.tim.checks.Check;
import me.tim.packets.impl.EntityAction;

import java.util.ArrayList;
import java.util.Objects;

public class NoSwing extends Check {
    private ArrayList<Byte> packetIDs;

    public NoSwing() {
        super("NoSwing", 15, true);
        this.packetIDs = new ArrayList<>();
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;
        this.packetIDs.add(event.getPacketId());

        if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY) {
            if (this.packetIDs.get(this.packetIDs.size() - 2) != PacketType.Play.Client.ARM_ANIMATION && Objects.equals(String.valueOf(new WrappedPacket(event.getNMSPacket()).readAnyObject(1)), EntityAction.ATTACK.toString()))
            {
                this.fail(event.getPlayer());
            }
        }
    }
}
