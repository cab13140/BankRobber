package fr.cab13140.bankrobber;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class BankMoney extends BukkitRunnable{
    private MainPlugin plugin = new MainPlugin();

    @Override
    public void run() {
        Location bankLocation = plugin.bank;
        Chest bank = (Chest) bankLocation.getBlock().getState();
        Inventory bankI = bank.getBlockInventory();
        int mpm = plugin.getConfig().getInt("moneyPerMinute");

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

        if (quantity <= plugin.getConfig().getInt("maxMoney")){
            if (bankI.firstEmpty() != -1) {
                bankI.addItem(new ItemStack(Material.GOLD_NUGGET,plugin.getConfig().getInt("nuggetsPerMinute")));
            }
        }
    }
}
