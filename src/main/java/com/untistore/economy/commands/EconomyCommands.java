package com.untistore.economy.commands;

import com.untistore.economy.managers.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyCommands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("economy.admin")) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("Usage: /economy <give/set/remove> <player> <amount>");
        }

        handleSubCommands(sender, args);
        return true;
    }

    /**
     * Handles the subcommands for the economy command.
     *
     * @param sender the command sender
     * @param args the command arguments
     */
    private void handleSubCommands(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Usage: /economy <give/set/remove> <player> <amount>");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "give", "add" -> handleGive(sender, args);
            case "set" -> handleSet(sender, args);
            case "remove" -> handleRemove(sender, args);
            default -> sender.sendMessage("Usage: /economy <give/set/remove> <player> <amount>");
        }
    }

    /**
     * Handles the give subcommand.
     *
     * @param sender the command sender
     * @param args the command arguments
     */
    private void handleGive(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);

        double amount = Double.parseDouble(args[2]);
        boolean success = EconomyManager.addAmount(target, amount);
        if (!success) {
            sender.sendMessage("Failed to add " + amount + " to " + target.getName());
            return;
        }

        sender.sendMessage("Added " + amount + " to " + target.getName());
    }

    /**
     * Handles the set subcommand.
     *
     * @param sender the command sender
     * @param args the command arguments
     */
    private void handleSet(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);

        double amount = Double.parseDouble(args[2]);
        boolean success = EconomyManager.setAmount(target, amount);
        if (!success) {
            sender.sendMessage("Failed to set " + target.getName() + "'s balance to " + amount);
            return;
        }

        sender.sendMessage("Set " + target.getName() + "'s balance to " + amount);
    }

    /**
     * Handles the remove subcommand.
     *
     * @param sender the command sender
     * @param args the command arguments
     */
    private void handleRemove(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[1]);

        double amount = Double.parseDouble(args[2]);
        boolean success = EconomyManager.removeAmount(target, amount);
        if (!success) {
            sender.sendMessage("Failed to remove " + amount + " from " + target.getName());
            return;
        }

        sender.sendMessage("Removed " + amount + " from " + target.getName());
    }

}
