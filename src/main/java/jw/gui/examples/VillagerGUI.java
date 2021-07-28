package jw.gui.examples;

import jw.gui.button.Button;
import jw.gui.core.InventoryGUI;
import jw.gui.core.InventoryGUIEventsHander;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;

import java.util.ArrayList;
import java.util.List;

public class VillagerGUI extends InventoryGUI
{
    private final Merchant merchant;
    private final List<MerchantRecipe> trades = new ArrayList<>();
    protected VillagerGUI(String name)
    {
        super(name, 1, InventoryType.MERCHANT);
        merchant = Bukkit.createMerchant(this.displayedName);
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    public Merchant getMerchant()
    {
        return merchant;
    }

    public void addTrade(MerchantRecipe merchantRecipe)
    {
        trades.add(merchantRecipe);
        merchant.setRecipes(trades);
    }
    public void addTrades(List<MerchantRecipe>  merchantRecipes)
    {
        trades.addAll(merchantRecipes);
        merchant.setRecipes(trades);
    }
    @Override
    protected int calculateSize(int height) {
        return 3;
    }
    public void open(Player player) {
        if (this.getParent()!= null) {
            this.getParent().close();
        }
        this.player = player;
        if (player != null && this.player.isOnline()) {
            this.onOpen(this.player);
            InventoryGUIEventsHander.Instnace().register(this);
            player.openMerchant(merchant,true);
        }
    }
    @Override
    protected void doClick(Player player, int index, ItemStack itemStack)
    {
        onTradeSelected(this.trades.get(index));
    }

    protected void onTradeSelected(MerchantRecipe merchantRecipe)
    {

    }

    @Override
    protected void onClick(Player player, Button button) {

    }

    @Override
    protected void onRefresh(Player player) {

    }

    @Override
    protected void onOpen(Player player) {

    }

    @Override
    protected void onClose(Player player) {

    }


}
