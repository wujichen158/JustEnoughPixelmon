package com.duckfox.jep.plugin.boss;

import com.pixelmonmod.pixelmon.enums.EnumBossMode;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BossDropRecipeWrapper {
    private final List<ItemStack> itemStacks;
    private final EnumBossMode mode;

    public BossDropRecipeWrapper(List<ItemStack> itemStacks, EnumBossMode mode) {
        this.itemStacks = itemStacks;
        this.mode = mode;
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public EnumBossMode getMode() {
        return mode;
    }
}
