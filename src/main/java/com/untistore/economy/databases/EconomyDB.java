package com.untistore.economy.databases;

import com.untistore.economy.managers.DatabaseManager;
import org.bukkit.Bukkit;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

public class EconomyDB {
    private static final String TABLE_NAME = "economy";
    private static final File file = new File(DatabaseManager.DATABASE_PATH);
    private static final DatabaseManager databaseManager = DatabaseManager.getInstance();

    static {
        createDatabaseFile();
        createTable();
    }

    /**
     * Creates the database file (economy.db) if it doesn't exist.
     */
    public static void createDatabaseFile() {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                Bukkit.getLogger().severe("Failed to create database file: " + e.getMessage());
            }
        }
    }

    /**
     * Creates the table (economy) if it doesn't exist.
     */
    public static void createTable() {
        String query = "create table if not exists " + TABLE_NAME + " (uuid varchar(36) primary key, balance DOUBLE)";
        Bukkit.getLogger().info("Creating table: " + query);

        try (Connection conn = databaseManager.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.executeUpdate();
            Bukkit.getLogger().info("Created table: " + TABLE_NAME);
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to create table: " + e.getMessage());
        }
    }

    /**
     * Checks if the table exists.
     *
     * @return true if the table exists, false otherwise
     */
    public static boolean doesTableExist() {
        String query = "select * from sqlite_master where type = 'table' and name = ?";

        try (Connection conn = databaseManager.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, TABLE_NAME);
            return ps.executeQuery().next();
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to check if table exists: " + e.getMessage());
            return false;
        }
    }

    /**
     * Inserts a player into the table.
     *
     * @param uuid the player's UUID
     */
    public static void insertPlayer(UUID uuid) {
        String query = "insert into economy (uuid, balance) values (?, ?)";

        try (Connection conn = databaseManager.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            ps.setDouble(2, 0.0);
            ps.executeUpdate();
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to insert player: " + e.getMessage());
        }
    }

    /**
     * Checks if a player is in the table.
     *
     * @param uuid the player's UUID
     * @return true if the player is in the table, false otherwise
     */
    public static boolean isPlayerInTable(UUID uuid) {
        String query = "select * from economy where uuid = ?";

        try (Connection conn = databaseManager.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            return ps.executeQuery().next();
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to check if player is in table: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets the balance of a player.
     *
     * @param uuid the player's UUID
     * @return the player's balance
     */
    public static double getBalance(UUID uuid) {
        String query = "select balance from economy where uuid = ?";

        try (Connection conn = databaseManager.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, uuid.toString());
            return ps.executeQuery().getDouble("balance");
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to get balance: " + e.getMessage());
            return 0.0;
        }
    }

    /**
     * Adds an amount to a player's balance.
     *
     * @param uuid the player's UUID
     * @param amount the amount to add
     */
    public static void addBalance(UUID uuid, double amount) {
        String query = "update economy set balance = balance + ? where uuid = ?";

        try (Connection conn = databaseManager.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to add balance: " + e.getMessage());
        }
    }

    /**
     * Sets the balance of a player.
     *
     * @param uuid the player's UUID
     * @param amount the amount to set
     */
    public static void setBalance(UUID uuid, double amount) {
        String query = "update economy set balance = ? where uuid = ?";

        try (Connection conn = databaseManager.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to set balance: " + e.getMessage());
        }
    }

    /**
     * Removes an amount from a player's balance.
     *
     * @param uuid the player's UUID
     * @param amount the amount to remove
     */
    public static void removeBalance(UUID uuid, double amount) {
        String query = "update economy set balance = balance - ? where uuid = ?";

        try (Connection conn = databaseManager.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (Exception e) {
            Bukkit.getLogger().severe("Failed to remove balance: " + e.getMessage());
        }
    }
}
