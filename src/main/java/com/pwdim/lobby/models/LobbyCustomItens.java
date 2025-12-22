package com.pwdim.lobby.models;

import com.pwdim.lobby.utils.ColorUtils;
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

public class LobbyCustomItens implements Listener {

    public Inventory getInv() {
        Inventory inv = Bukkit.createInventory(null, 27, ColorUtils.color("&bSelecione um Minigame"));

        ItemStack spleef = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta spleefMeta = spleef.getItemMeta();
        spleefMeta.setDisplayName(ColorUtils.color("&9&lSPLEEF"));

        ArrayList<String> spleefLore = new ArrayList<>();
        spleefLore.add(ColorUtils.color("&aClique para jogar!"));

        spleefMeta.setLore(spleefLore);
        spleef.setItemMeta(spleefMeta);

        inv.setItem(10, spleef);

        return inv;
    }

    public void compassItem(Player p) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();

        compassMeta.setDisplayName(ColorUtils.color("&b&lMINIGAMES"));

        ArrayList<String> compassLore = new ArrayList<>();
        compassLore.add(ColorUtils.color("&eClique com direito"));

        compassMeta.setLore(compassLore);
        compass.setItemMeta(compassMeta);


        p.getInventory().setItem(0, compass);
    }

    public void chestItem(Player p) {
        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();

        chestMeta.setDisplayName(ColorUtils.color("&6&LCOSMÉTICOS"));

        ArrayList<String> chestLore = new ArrayList<>();
        chestLore.add(ColorUtils.color("&eClique com direito"));

        chestMeta.setLore(chestLore);
        chest.setItemMeta(chestMeta);

        p.getInventory().setItem(4, chest);
    }

    public void lobbyItem(Player p) {
        ItemStack star = new ItemStack(Material.NETHER_STAR);
        ItemMeta starMeta = star.getItemMeta();

        starMeta.setDisplayName(ColorUtils.color("&d&lLOBBY'S"));

        ArrayList<String> starLore = new ArrayList<>();
        starLore.add(ColorUtils.color("&eClique com direito"));

        starMeta.setLore(starLore);
        star.setItemMeta(starMeta);

        p.getInventory().setItem(8, star);
    }

    @EventHandler
    public void pGiveItemOnJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        compassItem(p);
        lobbyItem(p);
        chestItem(p);
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
}
