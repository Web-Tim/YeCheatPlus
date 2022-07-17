package me.tim.checks.impl.combat.killaura;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import me.tim.checks.Check;
import me.tim.utils.Timer;
import me.tim.utils.packet.enums.EntityAction;
import org.bukkit.entity.Entity;

public class KillAuraD extends Check {
    private int deathAttacks;

    public KillAuraD() {
        super("KillAura (D)", 15, true);
        this.deathAttacks = 0;
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;

        if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY)
        {
            WrappedPacket packet = new WrappedPacket(event.getNMSPacket());
            if (this.getPacketUtil().readEnum(packet, 1).equals(EntityAction.ATTACK.name()))
            {
                for (Entity entity : event.getPlayer().getWorld().getEntities())
                {
                    if (entity != null && entity.getEntityId() == packet.readInt(0) && entity.isDead())
                    {
                        if (this.deathAttacks >= 5)
                        {
                            this.fail(event.getPlayer());
                            this.deathAttacks = 0;
                        }else{
                            this.deathAttacks++;
                        }
                    }
                }
            }
        }
    }
}
