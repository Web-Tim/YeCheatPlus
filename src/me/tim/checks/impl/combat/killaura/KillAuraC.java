package me.tim.checks.impl.combat.killaura;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import me.tim.YeCheatPlus;
import me.tim.checks.Check;
import me.tim.utils.PlayerData;
import me.tim.utils.io.Vec2;
import me.tim.utils.io.Vec3;
import me.tim.utils.packet.enums.EntityAction;
import me.tim.utils.player.Rotation;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;

public class KillAuraC extends Check {
    public KillAuraC() {
        super("KillAura (C)", 15, true);
    }

    @Override
    public void onPacket(PacketEvent e) {
        if (!(e instanceof PacketPlayReceiveEvent)) return;
        PacketPlayReceiveEvent event = (PacketPlayReceiveEvent) e;

        if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY) {
            WrappedPacket packet = new WrappedPacket(event.getNMSPacket());
            if (this.getPacketUtil().readEnum(packet, 1).equals(EntityAction.ATTACK.name())) {
                Rotation rotation = new Rotation();
                rotation.update();

                CraftEntity target = null;
                for (Entity entity : ((CraftWorld)event.getPlayer().getWorld()).getHandle().getWorld().getEntities())
                {
                    if (entity != null && entity.getEntityId() == packet.readInt(0))
                    {
                        target = (CraftEntity) entity;
                    }
                }

                if (target != null && (!rotation.rayCastPassed(target, 7) || !rotation.canEntityBeSeen(((CraftPlayer)event.getPlayer()), target)))
                {
                    this.fail(event.getPlayer());
                }
            }
        }
    }
}
