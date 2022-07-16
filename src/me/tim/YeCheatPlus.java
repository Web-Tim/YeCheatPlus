package me.tim;

import io.github.retrooper.packetevents.PacketEvents;
import me.tim.checks.CheckManager;
import me.tim.packets.PacketHandler;
import me.tim.utils.PlayerData;
import org.bukkit.plugin.java.JavaPlugin;

public class YeCheatPlus extends JavaPlugin {
    public CheckManager checkManager;
    public PacketEvents packetEvents;
    public PacketHandler packetHandler;
    public PlayerData playerData;

    private static YeCheatPlus instance;

    @Override
    public void onEnable() {
        YeCheatPlus.instance = this;
        this.playerData = new PlayerData();
        this.checkManager = new CheckManager();
        this.packetEvents.init();
        this.packetEvents.getEventManager().registerListener(this.packetHandler = new PacketHandler());
    }

    @Override
    public void onLoad() {
        this.packetEvents = PacketEvents.create(this);
        this.packetEvents.getSettings().getFallbackServerVersion();
        this.packetEvents.load();
    }

    @Override
    public void onDisable() {
        this.packetEvents.stop();
    }

    public static YeCheatPlus getInstance() {
        return instance;
    }
}
