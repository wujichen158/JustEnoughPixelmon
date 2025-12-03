package com.duckfox.jep.api.item;

import com.pixelmonmod.pixelmon.api.item.JsonItemStack;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PokeHeldItem {
    JsonItemStack jsonItemStack;
    ItemStack itemStack;

    public PokeHeldItem(JsonItemStack jsonItemStack) {
        this.jsonItemStack = jsonItemStack;
        itemStack = jsonItemStack.getItemStack();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public JsonItemStack getJsonItemStack() {
        return jsonItemStack;
    }

    public List<String> getTooltip() {
        List<String> list = new ArrayList<>();
        list.add("§r" + I18n.get("jep.tooltip.poke.helditem.chance", String.format("%.2f%%", jsonItemStack.percentChance)));
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PokeHeldItem item) {
            return jsonItemStack.equals(item.jsonItemStack);
        }
        return false;
    }
}
