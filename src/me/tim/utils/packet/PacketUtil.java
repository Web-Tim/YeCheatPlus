package me.tim.utils.packet;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import io.github.retrooper.packetevents.packetwrappers.api.SendableWrapper;
import org.bukkit.entity.Player;

public class PacketUtil {
    private PacketEvents eventAPI;

    public PacketUtil(PacketEvents eventAPI) {
        this.eventAPI = eventAPI;
    }

    public void sendPacket(Player player, SendableWrapper wPacket)
    {
        if (wPacket == null || this.eventAPI == null || player == null) return;
        this.eventAPI.getPlayerUtils().sendPacket(player, wPacket);
    }

    public boolean isRotationPacket(int id)
    {
        return id == PacketType.Play.Client.POSITION_LOOK || id == PacketType.Play.Client.LOOK;
    }

    public boolean isPositionPacket(int id)
    {
        return id == PacketType.Play.Client.POSITION_LOOK || id == PacketType.Play.Client.POSITION;
    }

    public boolean wasInventoryOpened(int id)
    {
        return id == PacketType.Play.Client.CLOSE_WINDOW;
    }

    // Important: Uses readAnyObject method
    public String readEnum(WrappedPacket packet, int id)
    {
        return String.valueOf(packet.readAnyObject(id));
    }
}
