package com.pwdim.lobby.itens;

import com.pwdim.lobby.LOBBY;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ProfileItem implements Listener {

    private final LOBBY plugin;

    public ProfileItem(LOBBY plugin) {
        this.plugin = plugin;
    }

    public Inventory getInv(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, MyUtils.color("&eSuas Preferências"));

        ItemStack scOnItem = new ItemStack(Material.INK_SACK, 1, (short) 10);
        ItemMeta scOnMeta = scOnItem.getItemMeta();

        List<String> scOnLore = new ArrayList<>();
        scOnLore.add(MyUtils.color("&eClique para desativar!"));
        scOnMeta.setLore(scOnLore);
        scOnMeta.setDisplayName(MyUtils.color("&d&lSTAFF-CHAT &a&lATIVADO"));
        scOnItem.setItemMeta(scOnMeta);


        ItemStack scOffItem = new ItemStack(Material.INK_SACK, 1, (short) 8);
        ItemMeta scOffMeta = scOffItem.getItemMeta();
        List<String> scOffLore = new ArrayList<>();
        scOffLore.add(MyUtils.color("&eClique para ativar!"));
        scOffMeta.setLore(scOffLore);
        scOffMeta.setDisplayName(MyUtils.color("&d&lSTAFF-CHAT &c&LDESATIVADO"));
        scOffItem.setItemMeta(scOffMeta);


        ItemStack scIcon = new ItemStack(Material.EMPTY_MAP, 1);
        ItemMeta scIconMeta = scIcon.getItemMeta();
        List<String> scIconLore = new ArrayList<>();
        scIconLore.add(MyUtils.color("&eDefine se você vai ou não receber mensagens do chat da equipe."));
        scIconMeta.setDisplayName(MyUtils.color("&dSTAFF CHAT"));
        scIconMeta.setLore(scIconLore);
        scIcon.setItemMeta(scIconMeta);


        if (p.hasPermission("staff.chat")) {
            inv.setItem(10, scIcon);
            if (plugin.getStaffChatOn().contains(p.getUniqueId())) {
                inv.setItem(19, scOnItem);
            } else {
                inv.setItem(19, scOffItem);
            }
        } else {
            return null;
        }

        return inv;
    }


    private ItemStack getHead(Player player) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(player.getName());
        skull.setOwner(player.getName());
        item.setItemMeta(skull);
        return item;
    }

    public void profileItem(Player p){
        ItemStack profile = getHead(p);
        SkullMeta skull = (SkullMeta) profile.getItemMeta();
        skull.setDisplayName(MyUtils.color("&e&lPERFIL"));
        skull.setOwner(p.getName());
        profile.setItemMeta(skull);
        p.getInventory().setItem(1, profile);

    }


    @EventHandler

    public void onPlayerClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (p.getItemInHand().getType() == Material.SKULL_ITEM) {

            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                p.openInventory(getInv(p));
            }
        }
    }


    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent e) {

        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        Inventory inv = e.getInventory();

        if (inv.getName().equals(MyUtils.color("&eSuas Preferências"))) {
            e.setCancelled(true);
            ItemStack scOnItem = new ItemStack(Material.INK_SACK, 1, (short) 10);
            ItemMeta scOnMeta = scOnItem.getItemMeta();
            List<String> scOnLore = new ArrayList<>();
            scOnLore.add(MyUtils.color("&eClique para desativar!"));
            scOnMeta.setLore(scOnLore);
            scOnMeta.setDisplayName(MyUtils.color("&d&lSTAFF-CHAT &a&lATIVADO"));
            scOnItem.setItemMeta(scOnMeta);


            ItemStack scOffItem = new ItemStack(Material.INK_SACK, 1, (short) 8);
            ItemMeta scOffMeta = scOffItem.getItemMeta();
            List<String> scOffLore = new ArrayList<>();
            scOffLore.add(MyUtils.color("&eClique para ativar!"));
            scOffMeta.setLore(scOffLore);
            scOffMeta.setDisplayName(MyUtils.color("&d&lSTAFF-CHAT &c&LDESATIVADO"));
            scOffItem.setItemMeta(scOffMeta);


            if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(scOnMeta.getDisplayName())) {
                plugin.getStaffChatOn().remove(p.getUniqueId());
                plugin.getStaffChatSync().remove(p.getUniqueId());
                inv.setItem(19, scOffItem);
                p.updateInventory();

            } else if (item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(scOffMeta.getDisplayName())) {
                plugin.getStaffChatOn().add(p.getUniqueId());
                inv.setItem(19, scOnItem);
                p.updateInventory();

            }
        }
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent e) {
        Player p = e.getPlayer();
        profileItem(p);
    }
} 