package me.calebtbw.minigamemechanics;

import com.google.common.collect.TreeMultimap;
import me.calebtbw.minigamemechanics.kits.Kit;
import me.calebtbw.minigamemechanics.kits.KitType;
import me.calebtbw.minigamemechanics.kits.types.Fighter;
import me.calebtbw.minigamemechanics.kits.types.Miner;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Arena {

    private int id;
    private ArrayList<UUID> players;
    private HashMap<UUID, Team> teams;
    private HashMap<UUID, Kit> kits;
    private Location spawn;
    private GameState state;
    private Countdown countdown;
    private Game game;

    public Arena(int id) {
        this.id = id;
        players = new ArrayList<>();
        teams = new HashMap<>();
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
        teams.clear();
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

        TreeMultimap<Integer, Team> count = TreeMultimap.create();
        for (Team team : Team.values()) {
            count.put(getTeamCount(team), team);
        }

        Team selected = (Team) count.values().toArray()[0];
        setTeam(player, selected);

        player.sendMessage(ChatColor.GRAY + "You were placed on " + selected.getDisplay() + ChatColor.GRAY + " team!");


        if (players.size() >= Config.getRequiredPlayers()) {
            countdown.begin();
        }
    }

    public void removePlayer(Player player) {
        players.remove(player.getUniqueId());
        player.teleport(Config.getLobbySpawn());

        removeTeam(player);

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
    public Team getTeam(Player player) { return teams.get(player.getUniqueId()); }
    public void setTeam(Player player, Team team) {
        removeTeam(player);
        teams.put(player.getUniqueId(), team);
    }
    public void removeTeam(Player player) {
        if (teams.containsKey(player.getUniqueId())) {
            teams.remove(player.getUniqueId());
        }
    }

    public int getTeamCount(Team team) {
        int amount = 0;
        for (Team t : teams.values()) {
            if (t.equals(team)) {
                amount++;
            }
        }
        return amount;
    }


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
