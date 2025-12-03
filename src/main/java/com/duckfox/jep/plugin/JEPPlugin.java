package com.duckfox.jep.plugin;

import com.duckfox.jep.utils.Resources;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEPPlugin implements IModPlugin {
    private List<DuckRecipeCategory<?>> categories = new ArrayList<>();

    @NotNull
    @Override
    public ResourceLocation getPluginUid() {
        return Resources.PLUGIN_UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
        categories.add(new com.duckfox.jep.plugin.pokemon.PokeDropRecipeCategory(helper));
        categories.add(new com.duckfox.jep.plugin.pokemon.PokeHeldRecipeCategory(helper));
        categories.add(new com.duckfox.jep.plugin.boss.BossDropRecipeCategory(helper));
        categories.add(new com.duckfox.jep.plugin.shopkeepers.ShopKeepersRecipeCategory(helper));
        categories.add(new com.duckfox.jep.plugin.den.RaidDropRecipeCategory(helper));
        categories.add(new com.duckfox.jep.plugin.fishing.FishingRecipeCategory(helper));
        categories.add(new com.duckfox.jep.plugin.infuser.InfuserRecipeCategory(helper));
        categories.add(new com.duckfox.jep.plugin.moveskill.ForageRecipeCategory(helper));
        categories.add(new com.duckfox.jep.plugin.moveskill.HeadbuttRecipeCategory(helper));
        categories.add(new com.duckfox.jep.plugin.moveskill.RockSmashRecipeCategory(helper));
        for (DuckRecipeCategory<?> category : categories) {
            registry.addRecipeCategories(category);
        }
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        var clientLevel = Minecraft.getInstance().level;
        if (clientLevel == null) {
            return;
        }

        RecipeManager recipeManager = clientLevel.getRecipeManager();
        for (DuckRecipeCategory<?> category : categories) {
            category.setup(registry);
        }
    }
}
