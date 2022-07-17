package me.tim.checks.impl.combat.killaura;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import me.tim.checks.Check;

import java.util.ArrayList;

public class KillAuraE extends Check {
    private ArrayList<Byte> packetList;

    public KillAuraE() {
        super("KillAura (E)", 15, true);
        this.packetList = new ArrayList<>();
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;
        WrappedPacket packet = new WrappedPacket(event.getNMSPacket());

        this.packetList.add(event.getPacketId());
        if (this.getPacketUtil().wasInventoryOpened(event.getPacketId()))
        {
            for (Byte b : this.packetList)
            {
                if (b == PacketType.Play.Client.USE_ENTITY)
                {
                    this.fail(event.getPlayer());
                }
            }
        }
    }
}
