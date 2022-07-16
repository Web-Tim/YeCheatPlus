package me.tim.packets;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.*;
import io.github.retrooper.packetevents.packetwrappers.api.SendableWrapper;
import io.github.retrooper.packetevents.packetwrappers.play.out.kickdisconnect.WrappedPacketOutKickDisconnect;
import me.tim.YeCheatPlus;
import me.tim.checks.Check;
import me.tim.utils.PlayerData;
import me.tim.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PacketHandler extends PacketListenerAbstract {

    @Override
    public void onPacketLoginReceive(PacketLoginReceiveEvent event) {
        this.registerChecks(event);
    }

    @Override
    public void onPacketHandshakeReceive(PacketHandshakeReceiveEvent event) {
        this.registerChecks(event);
    }

    @Override
    public void onPacketStatusReceive(PacketStatusReceiveEvent event) {
        this.registerChecks(event);
    }

    @Override
    public void onPostPacketPlayReceive(PostPacketPlayReceiveEvent event) {
        this.registerChecks(event);
    }

    @Override
    public void onPacketPlayReceive(PacketPlayReceiveEvent event) {
        if (YeCheatPlus.getInstance().playerData == null)
        {
            YeCheatPlus.getInstance().playerData = new PlayerData();
        }
        YeCheatPlus.getInstance().playerData.update(event);
        this.registerChecks(event);
    }

    @Override
    public void onPacketStatusSend(PacketStatusSendEvent event) {
        this.registerChecks(event);
    }

    @Override
    public void onPacketLoginSend(PacketLoginSendEvent event) {
        this.registerChecks(event);
    }

    @Override
    public void onPacketPlaySend(PacketPlaySendEvent event) {
        this.registerChecks(event);
    }

    @Override
    public void onPostPacketPlaySend(PostPacketPlaySendEvent event) {
        this.registerChecks(event);
    }

    private void registerChecks(PacketEvent event)
    {
        if (YeCheatPlus.getInstance().packetEvents == null || YeCheatPlus.getInstance().checkManager == null) return;

        for (Check check : YeCheatPlus.getInstance().checkManager.getChecks()) {
            if (check == null) continue;
            check.onPacket(event);

            if (check.isPunishable() && check.getVl() == check.getMaxVL() && event instanceof PacketPlayReceiveEvent) {
                this.sendPacket(((PacketPlayReceiveEvent) event).getPlayer(), new WrappedPacketOutKickDisconnect("Unfair advantage!"));
                check.setVl(0);
            }
        }
    }

    public void sendPacket(Player player, SendableWrapper wPacket)
    {
        if (wPacket == null || YeCheatPlus.getInstance().packetEvents == null || player == null) return;

        YeCheatPlus.getInstance().packetEvents.getPlayerUtils().sendPacket(player, wPacket);
    }
}
