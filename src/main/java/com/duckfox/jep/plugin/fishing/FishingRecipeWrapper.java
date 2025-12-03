package com.duckfox.jep.plugin.fishing;

import com.duckfox.jep.api.item.FishingItem;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FishingRecipeWrapper {
    private final List<FishingItem> fishingItems;
    private final ItemStack rod;
    private final boolean water;

    public FishingRecipeWrapper(List<FishingItem> fishingItems, ItemStack rod, boolean water) {
        this.fishingItems = fishingItems;
        this.rod = rod;
        this.water = water;
    }

    public List<FishingItem> getFishingItems() {
        return fishingItems;
    }

    public ItemStack getRod() {
        return rod;
    }

    public boolean isWater() {
        return water;
    }
}
