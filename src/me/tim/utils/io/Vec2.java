package me.tim.utils.io;

import io.github.retrooper.packetevents.utils.vector.Vector3i;

public class Vec2 {
    public final float yaw;

    public final float pitch;

    public Vec2(float yaw, float pitch)
    {
        if (yaw == -0.0D)
        {
            yaw = 0.0F;
        }

        if (pitch == -0.0D)
        {
            pitch = 0.0F;
        }

        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Vec2(Vector3i p_i46377_1_)
    {
        this(p_i46377_1_.getX(), p_i46377_1_.getY());
    }

    public Vec2 addVector(float yaw, float pitch)
    {
        return new Vec2(this.yaw + yaw, this.pitch + pitch);
    }

    public Vec2 difference(Vec2 vec)
    {
        float d0 = vec.yaw - this.yaw;
        float d1 = vec.pitch - this.pitch;
        return new Vec2(d0, d1);
    }
}
