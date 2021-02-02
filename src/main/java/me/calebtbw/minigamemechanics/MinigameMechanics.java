package me.calebtbw.minigamemechanics;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MinigameMechanics extends JavaPlugin {

    private static MinigameMechanics instance;

    @Override
    public void onEnable() {
        MinigameMechanics.instance = this;

        new Config(this);

        new Manager();

        getCommand("arena").setExecutor(new ArenaCommand());

        Bukkit.getPluginManager().registerEvents(new GameListener(),this);

    }

    public static MinigameMechanics getInstance() { return instance; }

}
