package me.calebtbw.minigamemechanics.kits;

import me.calebtbw.minigamemechanics.MinigameMechanics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.UUID;

public abstract class Kit implements Listener {

    protected UUID uuid;
    protected KitType type;

    public Kit(UUID uuid, KitType type) {
        this.uuid = uuid;
        this.type = type;

        Bukkit.getPluginManager().registerEvents(this, MinigameMechanics.getInstance());
    }

    public UUID getUuid() { return uuid; }
    public KitType getType() { return type; }

    public abstract void onStart(Player player);

    public void remove() {
        HandlerList.unregisterAll(this);
    }
}
