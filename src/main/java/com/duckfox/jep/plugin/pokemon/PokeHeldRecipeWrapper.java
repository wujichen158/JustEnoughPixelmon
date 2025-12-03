package com.duckfox.jep.plugin.pokemon;

import com.duckfox.jep.api.item.PokeHeldItem;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;

import java.util.List;

public class PokeHeldRecipeWrapper {
    private final EnumSpecies species;
    private final List<PokeHeldItem> heldItems;
    private final int form;

    public PokeHeldRecipeWrapper(EnumSpecies species, List<PokeHeldItem> heldItems) {
        this(species, heldItems, -1);
    }

    public PokeHeldRecipeWrapper(EnumSpecies species, List<PokeHeldItem> heldItems, int form) {
        this.species = species;
        this.heldItems = heldItems;
        this.form = form;
    }

    public EnumSpecies getSpecies() {
        return species;
    }

    public List<PokeHeldItem> getHeldItems() {
        return heldItems;
    }

    public int getForm() {
        return form;
    }
}
