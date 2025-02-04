package com.untistore.economy.commands;

import com.untistore.economy.managers.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && sender.hasPermission("economy.balance.other")) {
            Player target = Bukkit.getPlayer(args[0]);
            double balance = EconomyManager.getBalance(target);
            sender.sendMessage(target.getName() + "'s balance: " + balance);
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        Player player = (Player) sender;
        double balance = EconomyManager.getBalance(player);
        player.sendMessage("Your balance: " + balance);
        return true;
    }
}
