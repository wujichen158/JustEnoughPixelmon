package com.duckfox.jep.api.item;

import com.google.common.collect.Lists;
import com.pixelmonmod.pixelmon.config.PixelmonItemsPokeballs;
import com.pixelmonmod.pixelmon.config.PixelmonItemsTMs;
import com.pixelmonmod.pixelmon.items.ItemTechnicalMove;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RaidDrop {
    private ItemStack itemStack;
    private int weight;

    public RaidDrop(ItemStack itemStack, int weight) {
        this.itemStack = itemStack.copy();
        if (itemStack.getItem() instanceof ItemTechnicalMove)
        {
            this.itemStack = new ItemStack(PixelmonItemsTMs.gen8BlankTR);
            this.itemStack.setStackDisplayName("§n§lTR技能碟(本属性)");
        }
        this.weight = weight;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getWeight() {
        return weight;
    }
    public List<String> getTooltipText() {
        return Collections.singletonList(I18n.format("jep.tooltip.raid.drop.weight",weight));
    }
}
