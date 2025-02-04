package com.untistore.economy;

import com.untistore.economy.commands.BalanceCommands;
import com.untistore.economy.commands.EconomyCommands;
import com.untistore.economy.databases.EconomyDB;
import com.untistore.economy.managers.EconomyManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Economy plugin enabled");

        getCommand("economy").setExecutor(new EconomyCommands());
        getCommand("balance").setExecutor(new BalanceCommands());


        if (!EconomyDB.doesTableExist()) {
            getLogger().info("Creating economy table...");
            EconomyDB.createTable();
        }

        EconomyManager.addAllPlayersToDatabase();
    }

    @Override
    public void onDisable() {
        getLogger().info("Economy plugin disabled");
    }
}
