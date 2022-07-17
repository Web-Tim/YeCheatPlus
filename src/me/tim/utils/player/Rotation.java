package me.tim.utils.player;

import me.tim.YeCheatPlus;
import me.tim.utils.io.Vec2;
import me.tim.utils.io.Vec3;
import me.tim.utils.mc.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;

public class Rotation {
    private float lastYaw, lastPitch, yaw, pitch;
    private double lastPosX, lastPosY, lastPosZ, posX, posY, posZ;

    public Rotation() {
        this.posX = YeCheatPlus.getInstance().playerData.posX;
        this.posY = YeCheatPlus.getInstance().playerData.posY;
        this.posZ = YeCheatPlus.getInstance().playerData.posZ;
        this.lastPosX = YeCheatPlus.getInstance().playerData.lastPosX;
        this.lastPosY = YeCheatPlus.getInstance().playerData.lastPosY;
        this.lastPosZ = YeCheatPlus.getInstance().playerData.lastPosZ;

        this.yaw = YeCheatPlus.getInstance().playerData.yaw;
        this.pitch = YeCheatPlus.getInstance().playerData.pitch;
        this.lastYaw = YeCheatPlus.getInstance().playerData.lastYaw;
        this.lastPitch = YeCheatPlus.getInstance().playerData.lastPitch;
    }

    public void update()
    {
        this.posX = YeCheatPlus.getInstance().playerData.posX;
        this.posY = YeCheatPlus.getInstance().playerData.posY;
        this.posZ = YeCheatPlus.getInstance().playerData.posZ;
        this.lastPosX = YeCheatPlus.getInstance().playerData.lastPosX;
        this.lastPosY = YeCheatPlus.getInstance().playerData.lastPosY;
        this.lastPosZ = YeCheatPlus.getInstance().playerData.lastPosZ;

        this.yaw = YeCheatPlus.getInstance().playerData.yaw;
        this.pitch = YeCheatPlus.getInstance().playerData.pitch;
        this.lastYaw = YeCheatPlus.getInstance().playerData.lastYaw;
        this.lastPitch = YeCheatPlus.getInstance().playerData.lastPitch;
    }

    public float getSentYaw()
    {
        return this.yaw % 360;
    }

    public float getLastSentYaw()
    {
        return this.lastYaw % 360;
    }

    //SOON...
    public Vec2 gcdPrediction(float mouseSensitivity)
    {
        float s = mouseSensitivity * 0.6F + 0.2F;
        float gcd = s * s * s * 1.2F;
        float gcdYaw = this.getSentYaw() - this.getLastSentYaw();
        gcdYaw -= gcdYaw % gcd;

        float gcdPitch = this.pitch - this.lastPitch;
        gcdPitch -= gcdPitch % gcd;
        return new Vec2(gcdYaw, gcdPitch);
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

    public boolean canEntityBeSeen(CraftPlayer player, CraftEntity target)
    {
        return player.getHandle().world.rayTrace(new Vec3D(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), new Vec3D(target.getHandle().locX, target.getHandle().locY + (double)target.getHandle().getHeadHeight(), target.getHandle().locZ)) == null;
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
