package com.untistore.economy.managers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {
    private final HikariDataSource dataSource;
    public static DatabaseManager instance;
    public static final String DATABASE_PATH = "./plugins/UntiEco/economy.db";

    public DatabaseManager() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + DATABASE_PATH);
        dataSource = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void close() {
        dataSource.close();
    }
}
