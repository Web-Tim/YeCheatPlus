package me.tim.utils;

import io.github.retrooper.packetevents.event.impl.PacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import net.minecraft.server.v1_8_R3.BlockAir;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerData {
    public int groundTicks, airTicks;
    public boolean clientGround, realGround;
    public double posX, lastPosX, motionX;
    public double posY, lastPosY, motionY;
    public double posZ, lastPosZ, motionZ;
    public float yaw, lastYaw, yawChange;
    public float pitch, lastPitch, pitchChange;

    private int posTick;
    private int rotTick;

    public void update(PacketPlayReceiveEvent event)
    {
        WrappedPacket packet = new WrappedPacket(event.getNMSPacket());
        switch (event.getPacketId())
        {
            case PacketType.Play.Client.POSITION:
                this.setPosition(packet.readDouble(0), packet.readDouble(1), packet.readDouble(2));
                this.updateLastPos();
                this.clientGround = packet.readBoolean(0);
                this.realGround = this.getRealGround();
                break;
            case PacketType.Play.Client.POSITION_LOOK:
                this.setPosition(packet.readDouble(0), packet.readDouble(1), packet.readDouble(2));
                this.setRotation(packet.readFloat(0), packet.readFloat(1));
                this.updateLastPos();
                this.updateLastAngles();
                this.clientGround = packet.readBoolean(0);
                this.realGround = this.getRealGround();
                break;
            case PacketType.Play.Client.LOOK:
                this.setRotation(packet.readFloat(0), packet.readFloat(1));
                this.updateLastAngles();
                this.clientGround = packet.readBoolean(0);
                this.realGround = this.getRealGround();
                break;
        }
        this.updateGroundTicks();
    }

    private void setRotation(float yaw, float pitch)
    {
        this.yaw = yaw;
        this.pitch = pitch;
        this.yawChange = this.yaw - this.lastYaw;
        this.pitchChange = this.pitch - this.lastPitch;
    }

    private void updateLastAngles()
    {
        if (this.rotTick++ > 0)
        {
            this.lastYaw = this.yaw;
            this.lastPitch = this.pitch;
            this.rotTick = 0;
        }
    }

    private void setPosition(double posX, double posY, double posZ)
    {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.motionX = this.posX - this.lastPosX;
        this.motionY = this.posY - this.lastPosY;
        this.motionZ = this.posZ - this.lastPosZ;
    }

    private void updateLastPos()
    {
        if (this.posTick++ > 0)
        {
            this.lastPosX = this.posX;
            this.lastPosY = this.posY;
            this.lastPosZ = this.posZ;
            this.posTick = 0;
        }
    }

    private void updateGroundTicks()
    {
        if (this.realGround)
        {
            this.airTicks = 0;
            this.groundTicks++;
        }else{
            this.groundTicks = 0;
            this.airTicks++;
        }
    }

    private boolean getRealGround()
    {
        return this.posY % 1 / 64. < 1E-4;
    }

    public double getDistanceToGround(Player p)
    {
        Location loc = new Location(p.getWorld(), this.posX, this.posY, this.posZ);
        double yDiff = 0;
        while(loc.getBlock().getType() == Material.AIR) {
            yDiff+=1E-3;
            loc = new Location(p.getWorld(), this.posX, this.posY - yDiff, this.posZ);
        }
        return yDiff - 1E-3;
    }
}
