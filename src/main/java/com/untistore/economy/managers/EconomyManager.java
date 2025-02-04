package com.untistore.economy.managers;

import com.untistore.economy.databases.EconomyDB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EconomyManager {

    /**
     * Adds all online players to the database.
     */
    public static void addAllPlayersToDatabase() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!EconomyDB.isPlayerInTable(player.getUniqueId())) {
                EconomyDB.insertPlayer(player.getUniqueId());
            }
        }
    }

    /**
     * Removes the specified amount from the player's balance.
     *
     * @param target the player to remove the amount from
     * @param amount the amount to remove
     * @return true if the amount was removed successfully, false otherwise
     */
    public static boolean removeAmount(Player target, double amount) {
        if (target == null) return false;
        if (amount <= 0) return false;

        EconomyDB.removeBalance(target.getUniqueId(), amount);
        return true;
    }

    /**
     * Sets the player's balance to the specified amount.
     *
     * @param target the player to set the balance for
     * @param amount the amount to set
     * @return true if the balance was set successfully, false otherwise
     */
    public static boolean setAmount(Player target, double amount) {
        if (target == null) return false;
        if (amount < 0) return false;

        EconomyDB.setBalance(target.getUniqueId(), amount);
        return true;
    }

    /**
     * Adds the specified amount to the player's balance.
     *
     * @param target the player to add the amount to
     * @param amount the amount to add
     * @return true if the amount was added successfully, false otherwise
     */
    public static boolean addAmount(Player target, double amount) {
        if (target == null) return false;
        if (amount <= 0) return false;

        EconomyDB.addBalance(target.getUniqueId(), amount);
        return true;
    }

    /**
     * Gets the balance of the specified player.
     *
     * @param target the player to get the balance for
     * @return the player's balance
     */
    public static double getBalance(Player target) {
        return EconomyDB.getBalance(target.getUniqueId());
    }
}
