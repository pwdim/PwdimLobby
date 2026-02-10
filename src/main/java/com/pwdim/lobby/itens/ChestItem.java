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

public class ChestItem implements Listener {

    public Inventory getInv() {
        Inventory inv = Bukkit.createInventory(null, 27, MyUtils.color("&bSelecione um Cosmético"));

        ItemStack spleef = new ItemStack(Material.ENDER_CHEST);
        ItemMeta spleefMeta = spleef.getItemMeta();
        spleefMeta.setDisplayName(MyUtils.color("&3Cosméticos"));

        ArrayList<String> spleefLore = new ArrayList<>();
        spleefLore.add(MyUtils.color("&aClique para equipar!"));

        spleefMeta.setLore(spleefLore);
        spleef.setItemMeta(spleefMeta);

        inv.setItem(10, spleef);

        return inv;
    }

    public void chestItem(Player p) {
        ItemStack compass = new ItemStack(Material.CHEST);
        ItemMeta compassMeta = compass.getItemMeta();

        compassMeta.setDisplayName(MyUtils.color("&6&lCOSMÉSTICOS"));

        ArrayList<String> compassLore = new ArrayList<>();
        compassLore.add(MyUtils.color("&eClique com direito"));

        compassMeta.setLore(compassLore);
        compass.setItemMeta(compassMeta);


        p.getInventory().setItem(4, compass);
    }

    @EventHandler
    public void pDInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (p.getItemInHand().getType() == Material.CHEST) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                p.openInventory(getInv());

            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        chestItem(p);
    }
}
