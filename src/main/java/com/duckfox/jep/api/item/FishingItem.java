package com.duckfox.jep.api.item;

import com.pixelmonmod.pixelmon.api.spawning.conditions.SpawnCondition;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class FishingItem {
    private ItemStack itemStack;
    private float rarity;
    private SpawnCondition spawnCondition;
    private float percentage;

    public FishingItem(ItemStack itemStack, float rarity, float percentage, SpawnCondition spawnCondition) {
        this.itemStack = itemStack;
        this.rarity = rarity;
        this.spawnCondition = spawnCondition;
        this.percentage = percentage;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public float getRarity() {
        return rarity;
    }

    public SpawnCondition getSpawnCondition() {
        return spawnCondition;
    }

    public float getPercentage() {
        return percentage;
    }

    public List<String> getTooltip() {
        return Collections.emptyList();
    }
}
