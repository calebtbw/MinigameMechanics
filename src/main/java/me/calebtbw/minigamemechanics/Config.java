package me.calebtbw.minigamemechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Config {

    private static MinigameMechanics main;

    public Config(MinigameMechanics main) {
        Config.main = main;

        main.getConfig().options().copyDefaults();
        main.saveDefaultConfig();
    }

    public static int getRequiredPlayers() {
        return main.getConfig().getInt("required-players");
    }

    public static int getCountdownSeconds() {
        return main.getConfig().getInt("countdown-seconds");
    }

    public static Location getLobbySpawn() {
        return new Location(
                Bukkit.getWorld(main.getConfig().getString("lobby-spawn.world")),
                main.getConfig().getDouble("lobby-spawn.x"),
                main.getConfig().getDouble("lobby-spawn.y"),
                main.getConfig().getDouble("lobby-spawn.z"),
                main.getConfig().getInt("yaw"),
                main.getConfig().getInt("pitch"));
    }

    public static Location getArenaSpawn(int id) {
        World world = Bukkit.createWorld(new WorldCreator(main.getConfig().getString("arenas." + id + ".world")));
        world.setAutoSave(false);

        return new Location(
                world,
                main.getConfig().getDouble("arenas." + id + ".x"),
                main.getConfig().getDouble("arenas." + id + ".y"),
                main.getConfig().getDouble("arenas." + id + ".z"),
                main.getConfig().getInt("arenas." + id + ".yaw"),
                main.getConfig().getInt("arenas." + id + ".pitch"));
    }

    public static int getArenaAmount() {
        return main.getConfig().getConfigurationSection("arenas.").getKeys(false).size();
    }

}
