package me.calebtbw.minigamemechanics;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Arena arena;
    private int seconds;

    public Countdown(Arena arena) {
        this.arena = arena;
        this.seconds = Config.getCountdownSeconds();
    }

    public void begin() {
        arena.setState(GameState.COUNTDOWN);
        this.runTaskTimer(MinigameMechanics.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (seconds == 0) {
            cancel();
            arena.start();
            return;
        }

        if (seconds % 30 == 0 || seconds <= 10) {
            if (seconds == 1) {
                arena.sendMessage(ChatColor.AQUA + "Game will start in 1 second!");
            } else {
                arena.sendMessage(ChatColor.AQUA + "Game will start in" + seconds + "seconds.");

            }
        }

        if (arena.getPlayers().size() < Config.getRequiredPlayers()) {
            cancel();
            arena.setState(GameState.RECRUITING);
            arena.sendMessage(ChatColor.RED + "Too few players to begin! Countdown stopped.");
            return;
        }

        seconds--;
    }
}
