package com.duckfox.jep.api.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PokeDrop{

    int minDrop,maxDrop;
    public ItemStack item;
    public float rate;

    public PokeDrop(ItemStack item, int minDrop, int maxDrop, float rate) {
        this.item = item;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        this.rate = rate;
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> list = new LinkedList<>();
        if (item != null) list.add(item);
        return list;
    }

    public List<String> getTooltipText() {
        List<String> list = new ArrayList<>();
        list.add("§r"+I18n.format("jep.tooltip.poke.drop.min", minDrop));
        list.add("§r"+I18n.format("jep.tooltip.poke.drop.max", maxDrop));
        list.add("§r"+I18n.format("jep.tooltip.poke.drop.rate", String.format("%.2f%%", rate *100)));
        return list;
    }
}
