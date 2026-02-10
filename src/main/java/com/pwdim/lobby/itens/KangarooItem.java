package com.pwdim.lobby.itens;

import com.pwdim.lobby.utils.MyUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class KangarooItem implements Listener {

    public void kangarooItem(Player p) {
        ItemStack kangaroo = new ItemStack(Material.FIREWORK);
        ItemMeta kangarooMeta = kangaroo.getItemMeta();

        kangarooMeta.setDisplayName(MyUtils.color("&6Kangaroo"));

        ArrayList<String> kangarooLore = new ArrayList<>();

        kangarooMeta.setLore(kangarooLore);
        kangaroo.setItemMeta(kangarooMeta);


        p.getInventory().setItem(3, kangaroo);
    }

    @EventHandler

    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (p.getItemInHand().getType() == Material.FIREWORK) {

            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                e.setCancelled(true);
                if (p.isSneaking()){

                    if(p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR && p.getLocation().subtract(1, 0, 0).getBlock().getType() == Material.AIR && p.getLocation().subtract(0, 0, 1).getBlock().getType() == Material.AIR ){
                        return;
                    } else {
                        p.setVelocity(new Vector(p.getLocation().getDirection().getX()*3, 0.5, p.getLocation().getDirection().getZ()*3));
                    }
                } else {
                    if(p.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.AIR && p.getLocation().subtract(1, 0, 0).getBlock().getType() == Material.AIR && p.getLocation().subtract(0, 0, 1).getBlock().getType() == Material.AIR ){
                        return;
                    } else {
                        p.setVelocity(new Vector(p.getLocation().getDirection().getX(), 1.5, p.getLocation().getDirection().getZ()));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent e) {
        Player p = e.getPlayer();
        kangarooItem(p);
    }
}
