package com.pwdim.lobby.itens;


import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CompassItem implements Listener {

    public Inventory getInv() {
        Inventory inv = Bukkit.createInventory(null, 27, MyUtils.color("&bSelecione um Minigame"));

        ItemStack spleef = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta spleefMeta = spleef.getItemMeta();
        spleefMeta.setDisplayName(MyUtils.color("&9&lSPLEEF"));

        ArrayList<String> spleefLore = new ArrayList<>();
        spleefLore.add(MyUtils.color("&aClique para jogar!"));

        spleefMeta.setLore(spleefLore);
        spleef.setItemMeta(spleefMeta);

        inv.setItem(10, spleef);

        return inv;
    }

    public void compassItem(Player p) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();

        compassMeta.setDisplayName(MyUtils.color("&b&lMINIGAMES"));

        ArrayList<String> compassLore = new ArrayList<>();
        compassLore.add(MyUtils.color("&eClique com direito"));

        compassMeta.setLore(compassLore);
        compass.setItemMeta(compassMeta);


        p.getInventory().setItem(0, compass);
    }

    @EventHandler
    public void pDInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (p.getItemInHand().getType() == Material.COMPASS) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                p.openInventory(getInv());

            }
        }
    }
    @EventHandler
    public void onPClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ItemStack clicked = e.getCurrentItem();

        if (clicked == null || clicked.getType() == Material.AIR) {
            return;
        }

        if (clicked.getType() == Material.DIAMOND_HOE) {

        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        compassItem(p);
    }
}
