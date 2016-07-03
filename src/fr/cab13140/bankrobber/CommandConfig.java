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

import java.util.Objects;


@SuppressWarnings("FieldCanBeLocal")
public class CommandConfig implements CommandExecutor, Listener {
    private static final String CMD_MAXM = "maxmoney";
    private static String CMD_NPM = "npm";
    private static String CMD_NG = "nuggetsCost";
    MainPlugin p = MainPlugin.main;
    private ConsoleCommandSender cs = p.getServer().getConsoleSender();
    private boolean active = false;
    private Player activePlayer;

    public void onPlayerInteract(PlayerInteractEvent e){
        if (active && activePlayer == e.getPlayer() && e.getMaterial() == Material.CHEST) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("Chest registered");
            Location l = e.getClickedBlock().getLocation();
            p.getConfig().set("bank.x", l.getX());
            p.getConfig().set("bank.y", l.getY());
            p.getConfig().set("bank.z", l.getZ());
            p.getConfig().set("bank.world", l.getWorld().getName());
            active = false;
            activePlayer = null;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("bankrobber")){
                if (Objects.equals(args[0].toLowerCase(), "npm")) {
                    if (args.length == 2) {
                        if (NumberUtils.isNumber(args[1])) {
                            if (Integer.parseInt(args[1]) >= 0) {
                                if (sender instanceof Player)
                                    if (!((Player) sender).hasPermission("bankrobber.setconfig")) {
                                        ((Player) sender).sendMessage(ChatColor.RED + "Permission Denied");
                                        return true;
                                    }

                                // Begin of the real part
                                int npm = Integer.parseInt(args[1]);
                                FileConfiguration config = p.getConfig();
                                config.set("nuggetsperminute", npm);
                                if (sender instanceof Player) {
                                    Player p = (Player) sender;
                                    p.sendMessage(ChatColor.GREEN + "Config updated. Npm set to " + npm);
                                    p.sendMessage("Reload server or reboot for changes to take effects");
                                } else {
                                    cs.sendMessage("Config updated. Npm set to " + npm);
                                    cs.sendMessage("Reload server or reboot for changes to take effects");
                                }
                            } else if (sender instanceof Player) errorMessage(CMD_NPM, (Player) sender);
                            else errorMessage(CMD_NPM);
                        } else if (sender instanceof Player) errorMessage(CMD_NPM, (Player) sender);
                        else errorMessage(CMD_NPM);
                    } else {
                        if (sender instanceof Player) errorMessage(CMD_NPM, (Player) sender);
                        else errorMessage(CMD_NPM);
                    }
                }
            if (Objects.equals(args[0].toLowerCase(), "maxmoney")) {
                if (args.length == 2) {
                    if (NumberUtils.isNumber(args[1])) {
                        if (Integer.parseInt(args[1]) >= 1) {
                            if (sender instanceof Player)
                                if (!((Player) sender).hasPermission("bankrobber.setconfig")) {
                                    ((Player) sender).sendMessage(ChatColor.RED + "Permission Denied");
                                    return true;
                                }
                            // Begin of the real part
                            int maxmoney = Integer.parseInt(args[1]);
                            MainPlugin plugin = this.p;
                            FileConfiguration config = plugin.getConfig();
                            config.set("maxmoney", maxmoney);
                            if (sender instanceof Player) {
                                Player p = (Player) sender;
                                p.sendMessage(ChatColor.GREEN + "Config updated. Max money set to " + maxmoney);
                                p.sendMessage("Reload server or reboot for changes to take effects");
                            } else {
                                cs.sendMessage("Config updated. Max money set to " + maxmoney);
                                cs.sendMessage("Reload server or reboot for changes to take effects");
                            }

                        } else if (sender instanceof Player) errorMessage(CMD_MAXM, (Player) sender);
                        else errorMessage(CMD_MAXM);
                    } else if (sender instanceof Player) errorMessage(CMD_MAXM, (Player) sender);
                    else errorMessage(CMD_MAXM);
                } else {
                    if (sender instanceof Player) errorMessage(CMD_MAXM, (Player) sender);
                    else errorMessage(CMD_MAXM);
                }
            }
            if (Objects.equals(args[0].toLowerCase(),"bank")) {
                if (sender instanceof Player) {
                    if (!((Player) sender).hasPermission("bankrobber.setconfig")) {
                        ((Player) sender).sendMessage(ChatColor.RED + "Permission Denied");
                        return true;
                    }

                    Player p = (Player) sender;
                    p.sendMessage(ChatColor.GREEN + "Open a chest to set the bank");
                    active = true;
                    activePlayer = p;
                    PluginManager pm = this.p.getServer().getPluginManager();
                    pm.registerEvents(this, this.p);

                } else {
                    cs.sendMessage("This command must be executed in-game !");
                }
            }
            if (Objects.equals(args[0].toLowerCase(),"nuggetscost")) {
                if (args.length == 2) {
                    if (NumberUtils.isNumber(args[1])) {
                        if (Integer.parseInt(args[1]) >= 1) {
                            if (sender instanceof Player)
                                if (!((Player) sender).hasPermission("bankrobber.setConfig")) {
                                    ((Player) sender).sendMessage(ChatColor.RED + "Permission Denied");
                                    return true;
                                }
                            // Begin of the real part
                            int nuggetsCost = Integer.parseInt(args[1]);
                            MainPlugin plugin = this.p;
                            FileConfiguration config = plugin.getConfig();
                            config.set("nuggetscost", nuggetsCost);
                            if (sender instanceof Player) {
                                Player p = (Player) sender;
                                p.sendMessage(ChatColor.GREEN + "Config updated. Nuggets Cost set to " + nuggetsCost);
                                p.sendMessage("Reload server or reboot for changes to take effects");
                            } else {
                                cs.sendMessage("Config updated. Nuggets Cost set to " + nuggetsCost);
                                cs.sendMessage("Reload server or reboot for changes to take effects");
                            }

                        } else if (sender instanceof Player) errorMessage(CMD_NG, (Player) sender);
                        else errorMessage(CMD_NG);
                    } else if (sender instanceof Player) errorMessage(CMD_NG, (Player) sender);
                    else errorMessage(CMD_NG);
                } else {
                    if (sender instanceof Player) errorMessage(CMD_NG, (Player) sender);
                    else errorMessage(CMD_NG);
                }
            }
            return true;
        }else {
            return false;
        }
    }

    private void errorMessage(String command){
        switch (command) {
            case "npm":
                cs.sendMessage("Usage: /bankrobber npm (nuggetsperminute)");
            case "maxmoney":
                cs.sendMessage("Usage: /bankrobber maxmoney (maxbankmoney)");
            case "nuggetsCost":
                cs.sendMessage("Usage: /bankrobber nuggetsCost (moneyPerNuggets)");
        }
    }
    private void errorMessage(String command, Player p){
        switch (command){
            case "npm":
                p.sendMessage(ChatColor.RED + "Usage: /bankrobber npm (nuggetsperminute)");
            case "maxmoney":
                p.sendMessage("Usage: /bankrobber maxmoney (maxbankmoney)");
            case "nuggetsCost":
                p.sendMessage("Usage: /bankrobber nuggetsCost (moneyPerNuggets)");
        }
    }


}
