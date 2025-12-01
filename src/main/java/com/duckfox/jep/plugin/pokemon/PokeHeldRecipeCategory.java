package com.duckfox.jep.plugin.pokemon;

import com.duckfox.jep.api.item.PokeHeldItem;
import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.duckfox.jep.utils.PokeSprites;
import com.duckfox.jep.utils.Settings;
import com.pixelmonmod.pixelmon.api.item.JsonItemStack;
import com.pixelmonmod.pixelmon.api.spawning.SpawnInfo;
import com.pixelmonmod.pixelmon.api.spawning.SpawnSet;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.pokemon.SpawnInfoPokemon;
import com.pixelmonmod.pixelmon.config.PixelmonItemsHeld;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import java.util.*;

public class PokeHeldRecipeCategory extends DuckRecipeCategory<PokeHeldRecipeWrapper> {

    protected static final int X_FIRST_ITEM = 97;
    protected static final int Y_FIRST_ITEM = 12;

    public PokeHeldRecipeCategory(IGuiHelper helper) {
        super("pokeheld", "jep.pokeheld", helper.createBlankDrawable(170, 30), new ItemStack(PixelmonItemsHeld.destinyKnot));
    }

    @Override
    public void setupRecipes(IModRegistry registry) {
        List<PokeHeldRecipeWrapper> recipes = new ArrayList<>();

        // 1. 获取所有宝可梦种类并按全国编号排序
        List<EnumSpecies> sortedSpecies = new ArrayList<>(Arrays.asList(EnumSpecies.values()));
        sortedSpecies.sort(Comparator.comparingInt(EnumSpecies::getNationalPokedexInteger)); // 按编号升序


        Map<EnumSpecies, ArrayList<PokeHeldItem>> heldItems = new HashMap<>();
        HashSet<SpawnSet> allSets = new HashSet<>();
        allSets.addAll(PixelmonSpawning.standard);
        allSets.addAll(PixelmonSpawning.caveRock);
        allSets.addAll(PixelmonSpawning.fishing);
        allSets.addAll(PixelmonSpawning.forage);
        allSets.addAll(PixelmonSpawning.grass);
        allSets.addAll(PixelmonSpawning.headbutt);
        allSets.addAll(PixelmonSpawning.legendaries);
        allSets.addAll(PixelmonSpawning.megas);
        allSets.addAll(PixelmonSpawning.rocksmash);
        allSets.addAll(PixelmonSpawning.sweetscent);


        for (SpawnSet set : allSets) {
            for (SpawnInfo spawnInfo : set.spawnInfos) {
                if (spawnInfo instanceof SpawnInfoPokemon) {
                    SpawnInfoPokemon spawnInfoPokemon = (SpawnInfoPokemon) spawnInfo;
                    EnumSpecies species = spawnInfoPokemon.getSpecies();
                    if (spawnInfoPokemon.heldItems != null) {
                        if (!heldItems.containsKey(species)) {
                            heldItems.put(species, new ArrayList<>());
                        }
                        for (JsonItemStack item : spawnInfoPokemon.heldItems) {
                            PokeHeldItem heldItem = new PokeHeldItem(item);
                            if (!heldItems.get(species).contains(heldItem)) {
                                heldItems.get(species).add(heldItem);
                            }
                        }
                    }
                }
            }
        }

        for (EnumSpecies species : sortedSpecies) {
            if (heldItems.containsKey(species)) {
                recipes.add(new PokeHeldRecipeWrapper(species, heldItems.get(species)));
            }
        }

        // 3. 注册排序后的配方
        registry.addRecipes(recipes, getUid());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PokeHeldRecipeWrapper recipeWrapper, IIngredients iIngredients) {
        recipeLayout.getItemStacks().init(0, true, 30, 12);
        if (recipeWrapper.getForm()!=-10086)
        {
            recipeLayout.getItemStacks().set(0, PokeSprites.forceNewSpriteWithForm(recipeWrapper.getSpecies(),recipeWrapper.getForm()));
        }else {
            recipeLayout.getItemStacks().set(0, PokeSprites.getSprite(recipeWrapper.getSpecies()));
        }
        int xOffset = 0;
        int slot = 1;
        for (int i = 0; i < Settings.ITEMS_PER_ROW; i++) {
            recipeLayout.getItemStacks().init(slot++, false, X_FIRST_ITEM + xOffset, Y_FIRST_ITEM);
            xOffset += 72 / Settings.ITEMS_PER_ROW;
        }

        recipeLayout.getItemStacks().addTooltipCallback(recipeWrapper);
        slot = 1;
        for (int i = 0; i < Math.min(recipeWrapper.getHeldItems().size(), Settings.ITEMS_PER_ROW * Settings.ITEMS_PER_COLUMN); i++)
            recipeLayout.getItemStacks().set(slot++, recipeWrapper.getHeldItems().get(i).getItemStack());

    }
}
