package com.duckfox.jep.plugin;

import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class DuckRecipeCategory<T> implements IRecipeCategory<T> {
    private final String titleId;
    private final RecipeType<T> recipeType;
    private final IDrawable background;
    private final IDrawable icon;

    public DuckRecipeCategory(String titleId, RecipeType<T> recipeType, IDrawable background, IDrawable icon) {
        this.titleId = titleId;
        this.recipeType = recipeType;
        this.background = background;
        this.icon = icon;
    }

    public void setup(IRecipeRegistration reg) {
        reg.addRecipes(recipeType, getRecipes());
    }

    public abstract List<T> getRecipes();

    @NotNull
    @Override
    public Component getTitle() {
        return Component.translatable("jep.category." + titleId + ".name");
    }

    @Override
    public RecipeType<T> getRecipeType() {
        return recipeType;
    }

//    @Override
//    public IDrawable getBackground() {
//        return background;
//    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
}
