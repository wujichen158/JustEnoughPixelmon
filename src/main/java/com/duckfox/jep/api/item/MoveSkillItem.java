package com.duckfox.jep.api.item;

import com.pixelmonmod.pixelmon.api.moveskills.MoveSkill;
import com.pixelmonmod.pixelmon.api.spawning.conditions.SpawnCondition;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class MoveSkillItem {
    private final ItemStack itemStack;
    private final MoveSkill moveSkill;
    private final float rarity;
    private final SpawnCondition spawnCondition;

    public ItemStack getItemStack() {
        return itemStack;
    }
    public MoveSkill getMoveSkill() {
        return moveSkill;
    }
    public float getRarity() {
        return rarity;
    }
    public SpawnCondition getSpawnCondition() {
        return spawnCondition;
    }
    public MoveSkillItem(ItemStack itemStack, MoveSkill moveSkill, float rarity, SpawnCondition spawnCondition) {
        this.itemStack = itemStack;
        this.moveSkill = moveSkill;
        this.rarity = rarity;
        this.spawnCondition = spawnCondition;
    }

    public List<String> getTooltip()
    {
        return Collections.emptyList();
    }
}
