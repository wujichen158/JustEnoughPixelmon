package com.duckfox.jep.api.item;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class RaidDrop {
    private ItemStack itemStack;
    private int weight;

    public RaidDrop(ItemStack itemStack, int weight) {
        this.itemStack = itemStack.copy();
//        if (itemStack.getItem() instanceof TechnicalMoveItem) {
//            this.itemStack = new ItemStack(PixelmonItemsTMs.gen8BlankTR);
//            this.itemStack.setStackDisplayName("§n§lTR技能碟(本属性)");
//        }
        this.weight = weight;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getWeight() {
        return weight;
    }

    public List<String> getTooltipText() {
        return Collections.singletonList(I18n.get("jep.tooltip.raid.drop.weight", weight));
    }
}
