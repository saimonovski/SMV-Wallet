package io.github.saimonovski.smv.wallet.system.gems.core.objects;

import io.github.saimonovski.smv.wallet.Wallet;
import io.github.saimonovski.smv.wallet.api.object.Config;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class Database implements io.github.saimonovski.smv.wallet.api.object.Database {
    private final Config config;
    private final Connection connection;
    private final Wallet plugin;

    public Database(Wallet plugin) {
        this.plugin = plugin;
        this.config = plugin.getGemConfig();
        this.connection = initializeConnection();
    }

    private Connection initializeConnection() {
        try {
            boolean useMySQL = config.useMysql();
            if (useMySQL) {
                String host = config.databaseHostName();
                int port = config.databasePort();
                String database = config.databaseName();
                String password = config.databasePassword();
                String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";
                return DriverManager.getConnection(url, config.databaseUserName(), password);
            } else {
                String url = "jdbc:sqlite:" + this.plugin.getDataFolder() + File.separator + "wallet.db";
                return DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean connect() {
        try {
            initalizeTables();
            return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void initalizeTables() throws SQLException {
        String tableType = config.useMysql() ? "VARCHAR(36)" : "TEXT";
        String sql = "CREATE TABLE IF NOT EXISTS portfel_gems_users (" +
                "id " + tableType + " PRIMARY KEY," +
                "balance DOUBLE NOT NULL DEFAULT 0," +
                "cooldown DATETIME" +
                ")";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    @Override
    public double getBalance(UUID id) {
        String sql = "SELECT balance FROM portfel_gems_users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public void setBalance(UUID id, double newBalance) {
        String sql = "UPDATE portfel_gems_users SET balance = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setString(2, id.toString());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                sql = "INSERT INTO portfel_gems_users (id, balance) VALUES (?, ?)";
                try (PreparedStatement psInsert = connection.prepareStatement(sql)) {
                    psInsert.setString(1, id.toString());
                    psInsert.setDouble(2, newBalance);
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBalance(UUID id, double amount) {
        String sql = "UPDATE portfel_gems_users SET balance = balance + ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, id.toString());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                sql = "INSERT INTO portfel_gems_users (id, balance) VALUES (?, ?)";
                try (PreparedStatement psInsert = connection.prepareStatement(sql)) {
                    psInsert.setString(1, id.toString());
                    psInsert.setDouble(2, amount);
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeBalance(UUID id, double amount) {
        String sql = "UPDATE portfel_gems_users SET balance = balance - ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, id.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOnCooldown(UUID id) {
        LocalDateTime cooldownUntil = LocalDateTime.now().plusHours(24);
        String sql = "UPDATE portfel_gems_users SET cooldown = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(cooldownUntil));
            ps.setString(2, id.toString());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                sql = "INSERT INTO portfel_gems_users (id, balance, cooldown) VALUES (?, ?, ?)";
                try (PreparedStatement psInsert = connection.prepareStatement(sql)) {
                    psInsert.setString(1, id.toString());
                    psInsert.setDouble(2, 0);
                    psInsert.setTimestamp(3, Timestamp.valueOf(cooldownUntil));
                    psInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkCooldown(UUID id) {
        String sql = "SELECT cooldown FROM portfel_gems_users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp ts = rs.getTimestamp("cooldown");
                    if (ts == null) {
                        return false;
                    }
                    LocalDateTime cooldownTime = ts.toLocalDateTime();
                    return cooldownTime.isAfter(LocalDateTime.now());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
