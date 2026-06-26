package de.sirswiperlpp.realeconomy.Main;

import de.sirswiperlpp.realeconomy.Commands.EcoCommand;
import de.sirswiperlpp.realeconomy.Commands.PayCommand;
import de.sirswiperlpp.realeconomy.Listener.PlayerListener;
import de.sirswiperlpp.realeconomy.Provider.EcoProvider;
import de.sirswiperlpp.realeconomy.SQL.MySQL;
import de.sirswiperlpp.realeconomy.TabComplition.EcoTab;
import de.sirswiperlpp.realeconomy.TabComplition.PayTab;
import de.sirswiperlpp.realeconomy.Utils.Language;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.sql.SQLException;

public final class Main extends JavaPlugin {


    private static Main instance;
    public static FileConfiguration config;
    public static Language language;
    public static JedisPool jedisPool;

    public void loadConfiguration() {
        File datafolder = this.getDataFolder();
        File configFile = new File(datafolder + File.separator + "config.yml");

        if (!configFile.exists()) {
            this.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public Main() {
        instance = this;
        language = new Language(new File(getDataFolder(), "lang.ini"));
    }

    public static Main getInstance() {
        return instance;
    }

    private void checkAndCreateLanguageFile() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File languageFile = new File(getDataFolder(), "lang.ini");
        if (!languageFile.exists()) {
            getLogger().info("language.ini not found. Creating...");

            saveResource("lang.ini", true);
        }
    }

    @Override
    public void onEnable() {
        checkAndCreateLanguageFile();
        loadConfiguration();

        MySQL.connect();

        try {
            EcoProvider.createEcoTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JedisPoolConfig config = new JedisPoolConfig();

        jedisPool = new JedisPool(
                config,
                getConfig().getString("redis.host"),
                getConfig().getInt("redis.port"),
                2000,
                getConfig().getString("redis.pass")
        );

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);

        getCommand("pay").setExecutor(new PayCommand());
        getCommand("pay").setTabCompleter(new PayTab());
        getCommand("eco").setExecutor(new EcoCommand());
        getCommand("eco").setTabCompleter(new EcoTab());
    }

    @Override
    public void onDisable()
    {
        MySQL.disconnect();
        jedisPool.close();
    }
}
