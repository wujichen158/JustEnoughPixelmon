package com.duckfox.jep.utils;

import com.duckfox.jep.api.item.PokeDrop;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.entities.npcs.registry.PokemonDropInformation;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PackedDropInfo {
    PokemonDropInformation origin;
    public PokemonSpec pokemon;
    public ItemStack mainDrop;
    public ItemStack rareDrop;
    public ItemStack optDrop1;
    public ItemStack optDrop2;
    public int mainDropMin = 0;
    public int mainDropMax = 1;
    public int rareDropMin = 0;
    public int rareDropMax = 1;
    public int optDrop1Min = 0;
    public int optDrop1Max = 1;
    public int optDrop2Min = 0;
    public int optDrop2Max = 1;
    public PackedDropInfo(PokemonDropInformation origin)
    {
        this.origin = origin;
        this.pokemon = Reflections.get(origin, "pokemon", PokemonSpec.class);
        this.mainDrop = Reflections.get(origin, "mainDrop", ItemStack.class);
        this.rareDrop = Reflections.get(origin, "rareDrop", ItemStack.class);
        this.optDrop1 = Reflections.get(origin, "optDrop1", ItemStack.class);
        this.optDrop2 = Reflections.get(origin, "optDrop2", ItemStack.class);
        this.mainDropMin = Reflections.get(origin, "mainDropMin", Integer.class);
        this.mainDropMax = Reflections.get(origin, "mainDropMax", Integer.class);
        this.rareDropMin = Reflections.get(origin, "rareDropMin", Integer.class);
        this.rareDropMax = Reflections.get(origin, "rareDropMax", Integer.class);
        this.optDrop1Min = Reflections.get(origin, "optDrop1Min", Integer.class);
        this.optDrop1Max = Reflections.get(origin, "optDrop1Max", Integer.class);
        this.optDrop2Min = Reflections.get(origin, "optDrop2Min", Integer.class);
        this.optDrop2Max = Reflections.get(origin, "optDrop2Max", Integer.class);
    }

    public List<PokeDrop> getDrops()
    {
        List<PokeDrop> list = new ArrayList<>();
        if (mainDrop != null)
        {
            list.add(new PokeDrop(mainDrop, mainDropMin, mainDropMax,1));
        }
        if (rareDrop != null)
        {
            list.add(new PokeDrop(rareDrop, rareDropMin, rareDropMax,1));
        }
        if (optDrop1 != null)
        {
            list.add(new PokeDrop(optDrop1, optDrop1Min, optDrop1Max,1));
        }
        if (optDrop2 != null)
        {
            list.add(new PokeDrop(optDrop2, optDrop2Min, optDrop2Max,0.1f));
        }
        return list;
    }
}
