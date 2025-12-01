package com.duckfox.jep.plugin;

import com.duckfox.jep.plugin.boss.BossDropRecipeCategory;
import com.duckfox.jep.plugin.den.RaidDropRecipeCategory;
import com.duckfox.jep.plugin.fishing.FishingRecipeCategory;
import com.duckfox.jep.plugin.infuser.InfuserRecipeCategory;
import com.duckfox.jep.plugin.moveskill.ForageRecipeCategory;
import com.duckfox.jep.plugin.moveskill.HeadbuttRecipeCategory;
import com.duckfox.jep.plugin.moveskill.RockSmashRecipeCategory;
import com.duckfox.jep.plugin.pokemon.PokeDropRecipeCategory;
import com.duckfox.jep.plugin.pokemon.PokeHeldRecipeCategory;
import com.duckfox.jep.plugin.shopkeepers.ShopKeepersRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class JEPPlugin implements IModPlugin {

    private List<DuckRecipeCategory<?>> categories;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        categories = new ArrayList<>();
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
        categories.add(new PokeDropRecipeCategory(helper));
        categories.add(new PokeHeldRecipeCategory(helper));
        categories.add(new RaidDropRecipeCategory(helper));
        categories.add(new BossDropRecipeCategory(helper));
        categories.add(new ForageRecipeCategory(helper));
        categories.add(new HeadbuttRecipeCategory(helper));
        categories.add(new RockSmashRecipeCategory(helper));
        categories.add(new FishingRecipeCategory(helper));
        categories.add(new InfuserRecipeCategory(helper));
        categories.add(new ShopKeepersRecipeCategory(helper));

        for (DuckRecipeCategory<?> category : categories) {
            registry.addRecipeCategories(category);
        }
    }


    @Override
    public void register(IModRegistry registry) {
        for (DuckRecipeCategory<?> category : categories) {
            category.setup(registry);
        }
    }

}
