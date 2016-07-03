package fr.cab13140.bankrobber;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BankMoney extends BukkitRunnable{
    MainPlugin p = MainPlugin.main;

    @Override
    public void run() {
        Location bankLocation = p.bank;
        Chest bank = (Chest) bankLocation.getBlock().getState();
        Inventory bankI = bank.getBlockInventory();

        ItemStack[] inv = bankI.getContents();

        int quantity = 0;
        for (ItemStack anInv : inv) {
            if (anInv != null) {
                if (anInv.getType().equals(Material.GOLD_NUGGET)) {
                    int quant = anInv.getAmount();
                    quantity = quantity + quant;
                }
            }
        }

        if (quantity <= p.getConfig().getInt("maxMoney")){
            if (bankI.firstEmpty() != -1) {
                bankI.addItem(new ItemStack(Material.GOLD_NUGGET,p.getConfig().getInt("nuggetsPerMinute")));
            }
        }
    }
}
