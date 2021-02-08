package me.calebtbw.minigamemechanics.kits;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum KitType {

    MINER(ChatColor.GOLD + "Miner", Material.DIAMOND_PICKAXE, new String[]{ChatColor.GRAY + "The best Mining kit!"}),
    FIGHTER(ChatColor.RED + "Fighter", Material.IRON_SWORD, new String[]{ChatColor.GRAY + "The best melee kit!"});

    private String display;
    private Material material;
    private String[] description;


    private KitType(String display, Material material, String[] description) {
        this.display = display;
        this.material = material;
        this.description = description;
    }

    public String getDisplay() { return display; }
    public Material getMaterial() { return material; }
    public String[] getDescription() { return description; }
}
