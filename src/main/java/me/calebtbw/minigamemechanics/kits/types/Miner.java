package me.calebtbw.minigamemechanics.kits.types;

import me.calebtbw.minigamemechanics.kits.Kit;
import me.calebtbw.minigamemechanics.kits.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Miner extends Kit {

    public Miner(UUID uuid) {
        super(uuid, KitType.MINER);
    }

    @Override
    public void onStart(Player player) {
        player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
        player.getInventory().addItem(new ItemStack(Material.TORCH, 64));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        System.out.println(e.getPlayer().getName() + " just broke a block!");
    }
}
