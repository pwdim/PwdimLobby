package com.pwdim.lobby.itens;


import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class StarItem implements Listener {

    public Inventory getInv() {
        Inventory inv = Bukkit.createInventory(null, 36, MyUtils.color("&dSelecione um lOBBY"));

        ItemStack spleef = new ItemStack(Material.NETHER_STAR);
        ItemMeta spleefMeta = spleef.getItemMeta();
        spleefMeta.setDisplayName(MyUtils.color("&d&lLOBBY"));

        ArrayList<String> spleefLore = new ArrayList<>();
        spleefLore.add(MyUtils.color("&aClique para selecionar!"));

        spleefMeta.setLore(spleefLore);
        spleef.setItemMeta(spleefMeta);

        inv.setItem(10, spleef);

        return inv;
    }

    public void starItem(Player p) {
        ItemStack compass = new ItemStack(Material.NETHER_STAR);
        ItemMeta compassMeta = compass.getItemMeta();

        compassMeta.setDisplayName(MyUtils.color("&d&lLOBBY"));

        ArrayList<String> compassLore = new ArrayList<>();
        compassLore.add(MyUtils.color("&eClique com direito"));

        compassMeta.setLore(compassLore);
        compass.setItemMeta(compassMeta);


        p.getInventory().setItem(8, compass);
    }

    @EventHandler
    public void pDInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (p.getItemInHand().getType() == Material.NETHER_STAR) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                p.openInventory(getInv());

            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        starItem(p);
    }
}
