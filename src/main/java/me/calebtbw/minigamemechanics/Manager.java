package me.calebtbw.minigamemechanics;

import me.calebtbw.minigamemechanics.kits.KitType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private static ArrayList<Arena> arenas;

    public Manager() {
        arenas = new ArrayList<>();

        for (int i = 0; i <= (Config.getArenaAmount() - 1); i++) {
            arenas.add(new Arena(i));
        }
    }

    public static List<Arena> getArenas() { return arenas; }

    public static boolean isPlaying(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public static Arena getArena(Player player) {
        for (Arena arena : arenas) {
            if (arena.getPlayers().contains(player.getUniqueId())) {
                return arena;
            }
        }

        return null;
    }

    public static Arena getArena(int id) {
        for (Arena arena : arenas) {
            if (arena.getId() == id) {
                return arena;
            }
        }

        return null;
    }

    public static boolean isRecruiting(int id) { return getArena(id).getState() == GameState.RECRUITING; }

    public static boolean hasKit(Player player) {
        if (isPlaying(player)) {
            if (getArena(player).getKits().containsKey(player.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public static KitType getKit(Player player) {
        if (isPlaying(player)) {
            if (getArena(player).getKits().containsKey(player.getUniqueId())) {
                return getArena(player).getKits().get(player.getUniqueId()).getType();
            }
        }

        return null;
    }
}
