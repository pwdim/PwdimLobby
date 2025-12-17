package com.pwdim.lobby.listener;

//import com.pwdim.tutorial.TUTORIAL;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.*;

//public class VanishListener implements Listener {
//    private final TUTORIAL plugin;
//
//    public VanishListener(TUTORIAL plugin) {
//        this.plugin = plugin;
//    }
//
//
//    @EventHandler
//    public void onPreProcessCommand(PlayerCommandPreprocessEvent e) {
//        Player p = e.getPlayer();
//        // manu usa o /v e pwdim nao enxerga mais mas manu enxerga pwdim
//        // pwdim entra no vanish dps e enxerga a mwnuh mas mwnuh nao enxerga mais o pwdim msm no vanish
//        // mwnuh tem q executar um comando de para ver pwdim
//
//
//        for (Player target : Bukkit.getOnlinePlayers()) {
//
//            if (plugin.getVanishedPlayers().contains(target) ) {
//
//                if (!(p.hasPermission("staff.vanish"))) {
//                    p.hidePlayer(target);
//                    target.showPlayer(p);
//                } else {
//                    p.showPlayer(target);
//                    target.showPlayer(p);
//                }
//            }
//        }
//    }
//
//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent e) {
//        Player p = e.getPlayer();
//        // manu usa o /v e pwdim nao enxerga mais mas manu enxerga pwdim
//        // pwdim entra no vanish dps e enxerga a mwnuh mas mwnuh nao enxerga mais o pwdim msm no vanish
//        // mwnuh tem q executar um comando de para ver pwdim
//
//
//        for (Player target : Bukkit.getOnlinePlayers()) {
//
//            if (plugin.getVanishedPlayers().contains(target)) {
//
//                if (!(p.hasPermission("staff.vanish"))) {
//                    p.hidePlayer(target);
//                    target.showPlayer(p);
//                } else {
//                    p.showPlayer(target);
//                    target.showPlayer(p);
//                }
//            }
//        }
//    }
//
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent e) {
//        Player p = e.getPlayer();
//            for (Player target : Bukkit.getOnlinePlayers()) {
//
//                if (plugin.getVanishedPlayers().contains(target) ) {
//
//                    if (!(p.hasPermission("staff.vanish"))) {
//                        p.hidePlayer(target);
//                        target.showPlayer(p);
//                    } else {
//                        p.showPlayer(target);
//                        target.showPlayer(p);
//                    }
//                }
//            }
//    }
//    @EventHandler
//    public void onPlayerHit (PlayerChangedWorldEvent e) {
//        Player p = e.getPlayer();
//        for (Player target : Bukkit.getOnlinePlayers()) {
//
//            if (plugin.getVanishedPlayers().contains(target) ) {
//
//                if (!(p.hasPermission("staff.vanish"))) {
//                    p.hidePlayer(target);
//                    target.showPlayer(p);
//                } else {
//                    p.showPlayer(target);
//                    target.showPlayer(p);
//                }
//            }
//        }
//    }
//
//    @EventHandler
//    public void onPlayerLogin(PlayerLoginEvent e) {
//        Player p = e.getPlayer();
//        for (Player target : Bukkit.getOnlinePlayers()) {
//
//            if (plugin.getVanishedPlayers().contains(target) ) {
//
//                if (!(p.hasPermission("staff.vanish"))) {
//                    p.hidePlayer(target);
//                    target.showPlayer(p);
//                } else {
//                    p.showPlayer(target);
//                    target.showPlayer(p);
//                }
//            }
//        }
//    }
//    @EventHandler
//    public void onGameModeChange(PlayerGameModeChangeEvent e) {
//        Player p = e.getPlayer();
//        for (Player target : Bukkit.getOnlinePlayers()) {
//
//            if (plugin.getVanishedPlayers().contains(target) ) {
//
//                if (!(p.hasPermission("staff.vanish"))) {
//                    p.hidePlayer(target);
//                    target.showPlayer(p);
//                } else {
//                    p.showPlayer(target);
//                    target.showPlayer(p);
//                }
//            }
//        }
//    }
//
//}
