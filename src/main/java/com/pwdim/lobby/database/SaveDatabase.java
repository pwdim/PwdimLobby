package com.pwdim.lobby.database;

import com.pwdim.lobby.LOBBY;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//public class SaveDatabase {
//    private final LOBBY plugin;
//
//    public SaveDatabase(LOBBY plugin){
//        this.plugin = plugin;
//    }
//    public void savePlayerPrefs(String uuid, boolean vanish, boolean flying) {
//        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
//            String sql = "INSERT INTO playerPrefs (uuid, vanish, flying) VALUES (?, ?, ?) " +
//                    "ON DUPLICATE KEY UPDATE vanish = ?, flying = ?;";
//
//            try (Connection conn = database.getConnection();
//                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//                pstmt.setString(1, uuid);
//                pstmt.setBoolean(2, vanish);
//                pstmt.setBoolean(3, flying);
//                // Valores para o UPDATE caso o UUID já exista
//                pstmt.setBoolean(4, vanish);
//                pstmt.setBoolean(5, flying);
//
//                pstmt.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//}
