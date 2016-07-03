package fr.cab13140.bankrobber;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class MainPlugin extends JavaPlugin {
    private boolean enabled;
    private int mpm;
    private int maxMoney;
    private Location bank;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        getCommand("bankrobber").setExecutor(new CommandConfig());
        boolean bankSet = !config.getString("bank.world").isEmpty();

        //Get the configs

        enabled = config.getBoolean("enabled");
        mpm = config.getInt("moneyPerMinute");
        maxMoney = config.getInt("maxMoney");

        if (bankSet) {
            bank.setWorld(getServer().getWorld(config.getString("bank.world")));
            bank.setX(config.getDouble("bank.x"));
            bank.setY(config.getDouble("bank.y"));
            bank.setZ(config.getDouble("bank.z"));
        }



    }

    @Override
    public void onDisable() {

    }
}
