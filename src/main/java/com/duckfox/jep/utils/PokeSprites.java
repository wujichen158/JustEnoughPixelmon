package com.duckfox.jep.utils;

import com.pixelmonmod.api.SpecificationFactory;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonFactory;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.util.helpers.SpriteItemHelper;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

// TODO: 改下
public final class PokeSprites {
    private static final Map<Species, ItemStack> sprites = new HashMap<>();

    public static ItemStack getSprite(Species species) {
        if (sprites.containsKey(species)) {
            return sprites.get(species);
        }
        ItemStack photo = SpriteItemHelper.getPhoto(PokemonFactory.create(species)).copy();
        photo.getDisplayName().setStackDisplayName("§r§n" + species.getFormattedDex() + " " + species.getNameTranslation().getString());
        sprites.put(species, photo);
        return photo;
    }

    public static ItemStack forceNewSprite(Species species) {
        ItemStack photo = SpriteItemHelper.getPhoto(PokemonFactory.create(species)).copy();
        photo.setStackDisplayName("§r§n" + species.getFormattedDex() + " " + species.getNameTranslation().getString());
        if (!sprites.containsKey(species)) {
            sprites.put(species, photo);
        }
        return photo;
    }

    public static ItemStack forceNewSpriteWithForm(Species species, int form) {
        ItemStack photo = SpriteItemHelper.getPhoto(PokemonFactory.create(SpecificationFactory.from(species.name() + " form:" + form))).copy();
        photo.setStackDisplayName("§r§e§n" + species.getNationalPokedexNumber() + " " + species.getLocalizedName());
        return photo;
    }
}
