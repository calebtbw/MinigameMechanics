package me.calebtbw.minigamemechanics;

import me.calebtbw.minigamemechanics.kits.KitType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().contains("Kit Selection") && e.getRawSlot() <= 54 && e.getCurrentItem() !=null) {
            KitType type = KitType.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());

            if (Manager.hasKit(player) && Manager.getKit(player).equals(type)) {
                player.sendMessage(ChatColor.RED + "You have already equipped this kit!");
            } else {
                player.sendMessage(ChatColor.GREEN + " You have equipped the" + type.getDisplay() + ChatColor.GREEN + " kit!");
                Manager.getArena(player).setKit(player.getUniqueId(), type);
            }

            e.setCancelled(true);
            player.closeInventory();
        }

        if (e.getCurrentItem() !=null && e.getView().getTitle().contains("Team Selection") && e.getRawSlot() < 54) {
            Team team = Team.valueOf(e.getCurrentItem().getItemMeta().getLocalizedName());

            if (!Manager.getArena(player).getTeam(player).equals(team)) {
                player.sendMessage(ChatColor.GRAY + "You are now on " + team.getDisplay() + ChatColor.GRAY + " team!");
                Manager.getArena(player).setTeam(player, team);
            } else {
                player.sendMessage(ChatColor.GRAY + "You're already on " + team.getDisplay() + ChatColor.GRAY + " team!");
            }

            e.setCancelled(true);
            player.closeInventory();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        Player player = e.getPlayer();

        if (Manager.isPlaying(player) && Manager.getArena(player).getState().equals(GameState.LIVE)) {
            player.sendMessage(ChatColor.GOLD + "+1 Point!");

            Manager.getArena(player).getGame().addPoint(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        Player player = e.getPlayer();

        if (Manager.isPlaying(player)) {
            Manager.getArena(player).removePlayer(player);
        }
    }
}
