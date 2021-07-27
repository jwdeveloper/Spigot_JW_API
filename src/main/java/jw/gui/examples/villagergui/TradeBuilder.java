package jw.gui.examples.villagergui;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class TradeBuilder
{
    private final MerchantRecipe merchantRecipe;
    private final List<ItemStack> itemStacks = new ArrayList<>();
    public TradeBuilder(ItemStack itemStack)
    {
     merchantRecipe = new MerchantRecipe(itemStack,Integer.MAX_VALUE);
     merchantRecipe.setExperienceReward(false);
     merchantRecipe.setPriceMultiplier(0);
    }

    public TradeBuilder setMaxUsage(int maxUsage)
    {
        merchantRecipe.setMaxUses(maxUsage);
        return this;
    }
    public TradeBuilder setFirstItem(ItemStack item)
    {
        itemStacks.add(0,item);
        return this;
    }
    public TradeBuilder setSecondItem(ItemStack item)
    {
        itemStacks.add(1,item);
        return this;
    }
    public TradeBuilder setExperienceReward(boolean experienceReward)
    {
        merchantRecipe.setExperienceReward(experienceReward);
        return this;
    }
    public TradeBuilder setVillagerExperience(int experience)
    {
        merchantRecipe.setExperienceReward(true);
        merchantRecipe.setVillagerExperience(experience);
        return this;
    }
    public TradeBuilder setPriceMultiplier(int multiplier)
    {
        merchantRecipe.setPriceMultiplier(multiplier);
        return this;
    }
    public MerchantRecipe build()
    {
        merchantRecipe.setIngredients(itemStacks);
        return merchantRecipe;
    }
}
