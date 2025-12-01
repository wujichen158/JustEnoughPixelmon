package com.duckfox.jep.api.item;

import com.pixelmonmod.pixelmon.api.item.JsonItemStack;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PokeHeldItem {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PokeHeldItem) {
            PokeHeldItem item = (PokeHeldItem) obj;
            return jsonItemStack.itemID.equals(item.jsonItemStack.itemID) && Objects.equals(jsonItemStack.meta, item.jsonItemStack.meta) && Objects.equals(jsonItemStack.quantity, item.jsonItemStack.quantity) && Objects.equals(jsonItemStack.nbt, item.jsonItemStack.nbt) && Objects.equals(jsonItemStack.percentChance, item.jsonItemStack.percentChance);
        }
        return false;
    }

    JsonItemStack jsonItemStack;
    ItemStack itemStack;
    public PokeHeldItem(JsonItemStack jsonItemStack)
    {
        this.jsonItemStack = jsonItemStack;
        itemStack = jsonItemStack.getItemStack();
    }
    public ItemStack getItemStack()
    {
        return itemStack;
    }
    public JsonItemStack getJsonItemStack()
    {
        return jsonItemStack;
    }
    public List<String> getTooltip()
    {
        List<String> list = new ArrayList<>();
        list.add("§r"+I18n.format("jep.tooltip.poke.helditem.chance", String.format("%.2f%%", jsonItemStack.percentChance)));
        return list;
    }
}
