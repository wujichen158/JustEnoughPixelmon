package com.duckfox.jep.utils;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public final class PokeSprites {
    private static final Map<EnumSpecies, ItemStack> sprites = new HashMap<>();

    public static ItemStack getSprite(EnumSpecies species) {
        if (sprites.containsKey(species))
            return sprites.get(species);
        ItemStack photo = ItemPixelmonSprite.getPhoto(Pixelmon.pokemonFactory.create(species)).copy();
        photo.setStackDisplayName("§r§n"+species.getNationalPokedexNumber() + " " + species.getLocalizedName());
        sprites.put(species, photo);
        return sprites.get(species);
    }

    public static ItemStack forceNewSprite(EnumSpecies species) {
        ItemStack photo = ItemPixelmonSprite.getPhoto(Pixelmon.pokemonFactory.create(species)).copy();
        photo.setStackDisplayName("§r§n"+species.getNationalPokedexNumber() + " " + species.getLocalizedName());
        if (!sprites.containsKey(species)) {
            sprites.put(species, photo);
        }
        return photo;
    }
    public static ItemStack forceNewSpriteWithForm(EnumSpecies species, int form)
    {

        ItemStack photo = ItemPixelmonSprite.getPhoto(Pixelmon.pokemonFactory.create(PokemonSpec.from(species.name() + " form:" + form))).copy();
        photo.setStackDisplayName("§r§e§n"+species.getNationalPokedexNumber() + " " + species.getLocalizedName());
        return photo;
    }
}
