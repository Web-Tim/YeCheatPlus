package me.tim.utils.player;

import me.tim.utils.io.Vec2;
import me.tim.utils.io.Vec3;
import me.tim.utils.mc.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;

public class Rotation {
    private float lastYaw, lastPitch, yaw, pitch;
    private double lastPosX, lastPosY, lastPosZ, posX, posY, posZ;

    public Rotation(Vec3 position, Vec3 lastPosition, Vec2 rotation, Vec2 lastRotation) {
        this.posX = position.xCoord;
        this.posY = position.yCoord;
        this.posZ = position.zCoord;
        this.lastPosX = lastPosition.xCoord;
        this.lastPosY = lastPosition.yCoord;
        this.lastPosZ = lastPosition.zCoord;

        this.yaw = rotation.yaw;
        this.pitch = rotation.pitch;
        this.lastYaw = lastRotation.yaw;
        this.lastPitch = lastRotation.pitch;
    }

    public float getSentYaw()
    {
        return this.yaw % 360;
    }

    public boolean rayCastPassed(CraftEntity target, double range)
    {
        Vec3 vec3 = this.getPositionEyes(1.0F);
        Vec3 vec31 = this.getLook(1.0F);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
        AxisAlignedBB axisalignedbb = new AxisAlignedBB(target.getHandle().getBoundingBox().a,
                target.getHandle().getBoundingBox().b,
                target.getHandle().getBoundingBox().c,
                target.getHandle().getBoundingBox().d,
                target.getHandle().getBoundingBox().e,
                target.getHandle().getBoundingBox().f).expand(0.35, 0.35, 0.35);

        MovingObjectPosition movingObjectPosition = axisalignedbb.calculateIntercept(vec3, vec32);
        return movingObjectPosition != null;
    }

    public Vec3 getLook(float partialTicks)
    {
        if (partialTicks == 1.0F)
        {
            return this.getVectorForRotation(this.pitch, this.yaw);
        }
        else
        {
            float f = this.lastPitch + (this.pitch - this.lastPitch) * partialTicks;
            float f1 = this.lastYaw + (this.yaw - this.lastYaw) * partialTicks;
            return this.getVectorForRotation(f, f1);
        }
    }

    public Vec3 getPositionEyes(float partialTicks)
    {
        if (partialTicks == 1.0F)
        {
            return new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ);
        }
        else
        {
            double d0 = this.lastPosX + (this.posX - this.lastPosX) * (double)partialTicks;
            double d1 = this.lastPosY + (this.posY - this.lastPosY) * (double)partialTicks + this.getEyeHeight();
            double d2 = this.lastPosZ + (this.posZ - this.lastPosZ) * (double)partialTicks;
            return new Vec3(d0, d1, d2);
        }
    }

    public Vec3 getVectorForRotation(float pitch, float yaw)
    {
        double f = Math.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        double f1 = Math.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        double f2 = -Math.cos(-pitch * ((float)Math.PI / 180F));
        double f3 = Math.sin(-pitch * ((float)Math.PI / 180F));
        return new Vec3(f1 * f2, f3, f * f2);
    }

    public float getEyeHeight()
    {
        return 1.62F;
    }

    public boolean isIllegal()
    {
        return this.getSentYaw() > 360 || this.getSentYaw() < -360 || this.pitch < -90 || this.pitch > 90;
    }
}
