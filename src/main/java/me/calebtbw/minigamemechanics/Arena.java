package me.calebtbw.minigamemechanics;

import me.calebtbw.minigamemechanics.kits.Kit;
import me.calebtbw.minigamemechanics.kits.KitType;
import me.calebtbw.minigamemechanics.kits.types.Fighter;
import me.calebtbw.minigamemechanics.kits.types.Miner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Arena {

    private int id;
    private ArrayList<UUID> players;
    private HashMap<UUID, Kit> kits;
    private Location spawn;
    private GameState state;
    private Countdown countdown;
    private Game game;

    public Arena(int id) {
        this.id = id;
        players = new ArrayList<>();
        kits = new HashMap<>();
        spawn = Config.getArenaSpawn(id);
        state = GameState.RECRUITING;
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void start() {
        game.start();
    }

    public void reset() {
        for (UUID uuid : players) {
            removeKit(uuid);
            Bukkit.getPlayer(uuid).teleport(Config.getLobbySpawn());
            Bukkit.getPlayer(uuid).getInventory().clear();
        }

        state = GameState.RECRUITING;
        players.clear();
        countdown = new Countdown(this);
        game = new Game(this);
    }

    public void sendMessage(String message) {
        for (UUID uuid : players) {
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void addPlayer(Player player) {
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (players.size() >= Config.getRequiredPlayers()) {
            countdown.begin();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(Config.getLobbySpawn());

        removeKit(player.getUniqueId());

        player.getInventory().clear();

        if (players.size() <= Config.getRequiredPlayers() && state.equals(GameState.COUNTDOWN)) {
            reset();
        }

        if (players.size() == 0 && state.equals(GameState.LIVE)) {
            reset();
        }
    }

    public int getId() { return id; }
    public List<UUID> getPlayers() { return players; }
    public HashMap<UUID, Kit> getKits() { return kits; }
    public GameState getState() { return state; }
    public Game getGame() { return game; }

    public void setState(GameState state) { this.state = state; }


    public void removeKit(UUID uuid) {
        if (kits.containsKey(uuid)) {
            kits.get(uuid).remove();
            kits.remove(uuid);
        }
    }

    public void setKit(UUID uuid, KitType type) {
        removeKit(uuid);

        if (type.equals(KitType.FIGHTER)) {
            kits.put(uuid, new Fighter(uuid));
        } else if (type.equals(KitType.MINER)) {
            kits.put(uuid, new Miner(uuid));
        }
    }
}
