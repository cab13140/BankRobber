package fr.cab13140.bankrobber;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class MainPlugin extends JavaPlugin implements Listener {
    private boolean enabled;
    private int npm;
    private int maxMoney;
    Location bank;
    private BukkitRunnable bankMoney = new BankMoney();
    private boolean alarm;
    private BukkitRunnable bankAlarm = new BankAlarm();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        getCommand("bankrobber").setExecutor(new CommandConfig());
        boolean bankSet = !config.getString("bank.world").isEmpty();

        getServer().getPluginManager().registerEvents(this, this);

        //Get the configs

        enabled = config.getBoolean("enabled");
        npm = config.getInt("nuggetsPerMinute");
        maxMoney = config.getInt("maxMoney");

        if (bankSet) {
            bank.setWorld(getServer().getWorld(config.getString("bank.world")));
            bank.setX(config.getDouble("bank.x"));
            bank.setY(config.getDouble("bank.y"));
            bank.setZ(config.getDouble("bank.z"));
        }
        if (bankSet){
            // Run task every 600ticks (60seconds)
            bankMoney.runTaskTimer(this, 0L, 600L);
        }



    }

    @Override
    public void onDisable() {

    }

    public void onChestOpen(PlayerInteractEvent e){
        if (e.getClickedBlock().getState().getType() == Material.CHEST) {
            alarm = true;
            bankAlarm.runTaskTimer(this,0L,20L);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (alarm){
            if (sender.hasPermission("bankrobber.disablealarm") && command.getName().equalsIgnoreCase("alarm")){
                alarm = false;
                bankAlarm.cancel();
            }else
                return false;
        }
        return false;
    }
}
