package me.tim.checks.impl.combat;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import me.tim.YeCheatPlus;
import me.tim.checks.Check;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class Reach extends Check {
    public Reach() {
        super("Reach", 100, true);
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;

        if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY)
        {
            WrappedPacket packet = new WrappedPacket(event.getNMSPacket());
            CraftEntity entity = null;
            for (Entity en : ((CraftWorld)event.getPlayer().getWorld()).getHandle().getWorld().getEntities())
            {
                if (en != null && en.getEntityId() == packet.readInt(0))
                {
                    entity = ((CraftEntity)en);
                }
            }

            if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && YeCheatPlus.getInstance().playerData.getDistanceToEntity(entity) >= 3.42)
            {
                this.fail(event.getPlayer());
            }
        }
    }
}
