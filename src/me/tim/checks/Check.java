package me.tim.checks;

import io.github.retrooper.packetevents.event.PacketEvent;
import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.packetwrappers.api.SendableWrapper;
import me.tim.YeCheatPlus;
import me.tim.utils.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Check extends PacketListenerAbstract {
    private String name;
    private int vl, maxVL;
    private Timer vlTimer;
    private boolean punishable;

    public Check(String name, int maxVL, boolean punishable)
    {
        this.name = name;
        this.maxVL = maxVL;
        this.punishable = punishable;
        this.vlTimer = new Timer();
    }

    public void onPacket(PacketEvent event) { }

    public void fail(Player player)
    {
       this.vl++;
       this.handleVL();
       Bukkit.broadcastMessage("§7[§9YeCheatPlus§7]: §8" + player.getName() + " §7failed §b§4" + this.getName() + " §7(" + this.vl + " VL)");
    }

    public void handleVL()
    {
        if (this.vl > this.maxVL)
        {
            this.vl--;
        }

        if (this.vl == this.maxVL && this.vlTimer.hasTimePassed(10000))
        {
            this.vl = 0;
            this.vlTimer.reset();
        }
    }

    public void sendPacket(SendableWrapper packet, Player p)
    {
        YeCheatPlus.getInstance().packetHandler.sendPacket(p, packet);
    }


    public boolean isPunishable() {
        return punishable;
    }

    public int getMaxVL() {
        return maxVL;
    }

    public String getName() {
        return name;
    }

    public int getVl() {
        return vl;
    }
}