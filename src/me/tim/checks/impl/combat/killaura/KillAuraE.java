package me.tim.checks.impl.combat.killaura;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import me.tim.checks.Check;
import me.tim.utils.io.Tuple;
import me.tim.utils.packet.enums.EntityAction;

public class KillAuraE extends Check {
    private Tuple<Byte, WrappedPacket> packetList;

    public KillAuraE() {
        super("KillAura (E)", 15, true);

        this.packetList = new Tuple<>();
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;
        WrappedPacket packet = new WrappedPacket(event.getNMSPacket());

        //Not Working for now
        /*
        this.packetList.add(event.getPacketId(), packet);
        if (this.getPacketUtil().wasInventoryOpened(event.getPacketId()))
        {
            for (int i = 0; i < this.packetList.getFirst().size(); i++) {
                if (this.packetList.getFirst(i) == PacketType.Play.Client.USE_ENTITY)
                {
                    if (this.packetList.getSecond(i) != null && this.getPacketUtil().readEnum(this.packetList.getSecond(i), 1).equals(EntityAction.ATTACK.name()))
                    {
                        this.fail(event.getPlayer());
                    }
                }
            }
        }*/

    }
}
