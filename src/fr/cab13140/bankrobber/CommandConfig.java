package fr.cab13140.bankrobber;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;


@SuppressWarnings("FieldCanBeLocal")
public class CommandConfig implements CommandExecutor, Listener {
    private static final String CMD_MAXM = "maxmoney";
    private static String CMD_MPM = "mpm";
    private MainPlugin plugin = new MainPlugin();
    private ConsoleCommandSender cs = plugin.getServer().getConsoleSender();

    public void onPlayerInteract(PlayerInteractEvent e){
        if (e.getMaterial() == Material.CHEST){
            e.setCancelled(true);
            e.getPlayer().sendMessage("Chest registered");
            Location l = e.getClickedBlock().getLocation();
            plugin.getConfig().set("bank.x", l.getX());
            plugin.getConfig().set("bank.y", l.getY());
            plugin.getConfig().set("bank.z", l.getZ());
            plugin.getConfig().set("bank.world", l.getWorld().getName());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("bankrobber")){
            switch (args[0].toLowerCase()) {
                case "mpm":
                    if (args.length == 2){
                        if (NumberUtils.isNumber(args[1])){
                            if (Integer.parseInt(args[1]) >= 0){
                                if (sender instanceof Player)
                                    if (!((Player)sender).hasPermission("bankrobber.setConfig")){
                                        ((Player)sender).sendMessage(ChatColor.RED + "Permission Denied");
                                        return true;
                                    }

                                // Begin of the real part
                                int mpm = Integer.parseInt(args[1]);
                                MainPlugin plugin = new MainPlugin();
                                FileConfiguration config = plugin.getConfig();
                                config.set("moneyPerMinute",mpm);
                                if (sender instanceof Player){
                                    Player p = (Player) sender;
                                    p.sendMessage(ChatColor.GREEN + "Config updated. Mpm set to " + mpm);
                                    p.sendMessage("Reload server or reboot for changes to take effects");
                                }else{
                                    cs.sendMessage("Config updated. Mpm set to " + mpm);
                                    cs.sendMessage("Reload server or reboot for changes to take effects");
                                }
                            }else if (sender instanceof Player) errorMessage(CMD_MPM, (Player)sender); else errorMessage(CMD_MPM);
                        }else if (sender instanceof Player) errorMessage(CMD_MPM, (Player)sender); else errorMessage(CMD_MPM);
                    }else{
                        if (sender instanceof Player) errorMessage(CMD_MPM, (Player)sender); else errorMessage(CMD_MPM);
                    }

                case "maxmoney":
                    if (args.length == 2){
                        if (NumberUtils.isNumber(args[1])){
                            if (Integer.parseInt(args[1]) >= 1){
                                if (sender instanceof Player)
                                    if (!((Player)sender).hasPermission("bankrobber.setConfig")){
                                        ((Player)sender).sendMessage(ChatColor.RED + "Permission Denied");
                                        return true;
                                    }
                                // Begin of the real part
                                int maxmoney = Integer.parseInt(args[1]);
                                MainPlugin plugin = new MainPlugin();
                                FileConfiguration config = plugin.getConfig();
                                config.set("maxMoney",maxmoney);
                                if (sender instanceof Player){
                                    Player p = (Player) sender;
                                    p.sendMessage(ChatColor.GREEN + "Config updated. Max money set to " + maxmoney);
                                    p.sendMessage("Reload server or reboot for changes to take effects");
                                }else{
                                    cs.sendMessage("Config updated. Max money set to " + maxmoney);
                                    cs.sendMessage("Reload server or reboot for changes to take effects");
                                }

                            }else if (sender instanceof Player) errorMessage(CMD_MAXM, (Player)sender); else errorMessage(CMD_MAXM);
                        }else if (sender instanceof Player) errorMessage(CMD_MAXM, (Player)sender); else errorMessage(CMD_MAXM);
                    }else{
                        if (sender instanceof Player) errorMessage(CMD_MAXM, (Player)sender); else errorMessage(CMD_MAXM);
                    }
                case "bank":
                    if (sender instanceof Player){
                        if (!((Player)sender).hasPermission("bankrobber.setConfig")){
                            ((Player)sender).sendMessage(ChatColor.RED + "Permission Denied");
                            return true;
                        }

                        Player p = (Player) sender;
                        p.sendMessage(ChatColor.GREEN + "Open a chest to set the bank");
                        PluginManager pm = new MainPlugin().getServer().getPluginManager();
                        pm.registerEvents(this, new MainPlugin());

                    }else{
                        cs.sendMessage("This command must be executed in-game !");
                    }
                default:
                    //TODO: Help on command
            }
            return true;
        }else {
            return false;
        }
    }

    private void errorMessage(String command){
        switch (command) {
            case "mpm":
                cs.sendMessage("Usage: /bankrobber mpm (moneyperminute)");
        }
    }
    private void errorMessage(String command, Player p){
        switch (command){
            case "mpm":
                p.sendMessage(ChatColor.RED + "Usage: /bankrobber mpm (moneyperminute)");
        }
    }


}
