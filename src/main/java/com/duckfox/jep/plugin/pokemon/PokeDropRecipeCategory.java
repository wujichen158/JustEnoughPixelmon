package com.duckfox.jep.plugin.pokemon;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.duckfox.jep.utils.PackedDropInfo;
import com.duckfox.jep.utils.PokeSprites;
import com.duckfox.jep.utils.Settings;
import com.pixelmonmod.pixelmon.config.PixelmonItems;
import com.pixelmonmod.pixelmon.config.PixelmonItemsPokeballs;
import com.pixelmonmod.pixelmon.entities.npcs.registry.DropItemRegistry;
import com.pixelmonmod.pixelmon.entities.npcs.registry.PokemonDropInformation;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import java.util.*;

public class PokeDropRecipeCategory extends DuckRecipeCategory<PokeDropRecipeWrapper> {

    protected static final int X_FIRST_ITEM = 97;
    protected static final int Y_FIRST_ITEM = 12;

    public PokeDropRecipeCategory(IGuiHelper helper) {
        super("pokedrop", "jep.pokedrop", helper.createBlankDrawable(170, 30), new ItemStack(PixelmonItemsPokeballs.masterBall));
    }

    @Override
    public void setupRecipes(IModRegistry registry) {
        List<PokeDropRecipeWrapper> recipes = new ArrayList<>();

        // 1. 获取所有宝可梦种类并按全国编号排序
        List<EnumSpecies> sortedSpecies = new ArrayList<>(DropItemRegistry.pokemonDrops.keySet());
        sortedSpecies.sort(Comparator.comparingInt(EnumSpecies::getNationalPokedexInteger)); // 按编号升序

        for (EnumSpecies species : sortedSpecies) {
            for (PokemonDropInformation information : DropItemRegistry.pokemonDrops.get(species)) {
                PackedDropInfo info = new PackedDropInfo(information);
                if (info.pokemon.form!=null)
                {
                    recipes.add(new PokeDropRecipeWrapper(species, info.getDrops(), info.pokemon.form));
                }else {
                    recipes.add(new PokeDropRecipeWrapper(species, info.getDrops()));
                }
            }
        }

        // 3. 注册排序后的配方
        registry.addRecipes(recipes, getUid());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PokeDropRecipeWrapper recipeWrapper, IIngredients iIngredients) {
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
        for (int i = 0; i < Math.min(recipeWrapper.getDrops().size(), Settings.ITEMS_PER_ROW * Settings.ITEMS_PER_COLUMN); i++)
            recipeLayout.getItemStacks().set(slot++, recipeWrapper.getDrops().get(i).getDrops());

    }
}
