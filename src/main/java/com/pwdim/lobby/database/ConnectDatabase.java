package com.pwdim.lobby.database;

import com.pwdim.lobby.utils.MyUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDatabase {
    private HikariDataSource dataSource;

    public void setup() {
        ConfigurationSection section = Bukkit.getPluginManager().getPlugin("Lobby").getConfig().getConfigurationSection("database");

        if (section == null) {
            Bukkit.getConsoleSender().sendMessage(MyUtils.color("&c[Lobby] Erro: Seção 'database' não encontrada no config.yml!"));
            return;
        }

        HikariConfig config = new HikariConfig();

        String host = section.getString("host", "localhost");
        String name = section.getString("name", "users");
        String user = section.getString("user", "root");
        String password = section.getString("password", "");
        String port = section.getString("port", "3306");

        config.setJdbcUrl("jdbc:mariadb://" + host + ":" + port + "/" + name);
        config.setUsername(user);
        config.setPassword(password);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        this.dataSource = new HikariDataSource(config);

        createTables();
    }

    private void createTables() {
        String playerData = "CREATE TABLE IF NOT EXISTS playerData (" +
                "playerServerID INT PRIMARY KEY AUTO_INCREMENT," +
                "uuid VARCHAR(36) NOT NULL UNIQUE," +
                "firstLogin DATE," +
                "lastLogin DATE," +
                "tag VARCHAR(50)," +
                "premium BOOLEAN DEFAULT TRUE," +
                "lastVersion VARCHAR(10)," +
                "lastIp VARCHAR(20));";

        String playerPref = "CREATE TABLE IF NOT EXISTS playerPrefs(" +
                "uuid VARCHAR(36) PRIMARY KEY," +
                "staffChatToggle BOOLEAN NOT NULL DEFAULT FALSE," +
                "staffChatLinked BOOLEAN NOT NULL DEFAULT FALSE," +
                "vanish BOOLEAN NOT NULL DEFAULT FALSE," +
                "vanishVisualize BOOLEAN NOT NULL DEFAULT FALSE," +
                "flying BOOLEAN NOT NULL DEFAULT FALSE," +
                "FOREIGN KEY (uuid) REFERENCES playerData(uuid) ON DELETE CASCADE);";
        String spawnsData = "CREATE TABLE IF NOT EXISTS spawnsData(" +
                "spawnID INT PRIMARY KEY AUTO_INCREMENT," +
                "server VARCHAR(100) NOT NULL," +
                "location_world VARCHAR(100) NOT NULL," +
                "location_x DOUBLE NOT NULL," +
                "location_y DOUBLE NOT NULL," +
                "location_z DOUBLE NOT NULL," +
                "location_yaw FLOAT NOT NULL," +
                "location_pitch float NOT NULL);";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(playerData);
            statement.executeUpdate(playerPref);
            statement.executeUpdate(spawnsData);

            Bukkit.getConsoleSender().sendMessage(MyUtils.color("&a[Lobby] Tabelas do banco de dados verificadas/criadas com sucesso!"));

        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(MyUtils.color("&4[ERRO] &cFalha ao criar tabelas: " + e.getMessage()));
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("O DataSource não foi inicializado corretamente.");
        }
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}