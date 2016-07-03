package fr.cab13140.bankrobber;

import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by danit on 03/07/2016.
 */
public class BankAlarm extends BukkitRunnable {
    MainPlugin p = MainPlugin.main;
    @Override
    public void run() {
        World w = p.bank.getWorld();
        w.playSound(p.bank, Sound.BLOCK_ANVIL_PLACE, 20L, 1L);
    }
}
