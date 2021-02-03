package me.calebtbw.minigamemechanics.kits.types;

import me.calebtbw.minigamemechanics.kits.Kit;
import me.calebtbw.minigamemechanics.kits.KitType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Fighter extends Kit {

    public Fighter(UUID uuid) {
        super(uuid, KitType.FIGHTER);
    }


    @Override
    public void onStart(Player player) {
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        System.out.println(e.getDamager().getName() + " just damaged " + e.getEntity().getName());
    }
}
