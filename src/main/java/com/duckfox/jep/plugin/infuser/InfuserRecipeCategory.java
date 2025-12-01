package com.duckfox.jep.plugin.infuser;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.duckfox.jep.utils.Reflections;
import com.pixelmonmod.pixelmon.api.recipe.InfuserRecipes;
import com.pixelmonmod.pixelmon.client.gui.machines.infuser.GuiInfuser;
import com.pixelmonmod.pixelmon.config.PixelmonBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.config.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class InfuserRecipeCategory extends DuckRecipeCategory<InfuserRecipeWrapper> {

    private final IDrawableAnimated animatedFlame;
    private final IDrawableAnimated animatedProgress;

    public InfuserRecipeCategory(IGuiHelper helper) {
        super("infuser", "jep.infuser", helper.drawableBuilder(GuiInfuser.infuserGuiTextures, 25, 13, 135, 50).build(), new ItemStack(PixelmonBlocks.infuser));
        this.animatedFlame = helper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14).buildAnimated(300, IDrawableAnimated.StartDirection.TOP, true);
        this.animatedProgress = helper.drawableBuilder(GuiInfuser.infuserGuiTextures, 180, 14, 15, 13).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        animatedFlame.draw(minecraft, 4, 0);
        animatedProgress.draw(minecraft, 83, 19);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setupRecipes(IModRegistry registry) {
        List<InfuserRecipeWrapper> recipes = new ArrayList<>();

        Map<Tuple<ItemStack, ItemStack>, Tuple<ItemStack, Integer>> resultsList = (Map<Tuple<ItemStack, ItemStack>, Tuple<ItemStack, Integer>>) Reflections.get(InfuserRecipes.instance(), "resultsList", Map.class);

        for (Map.Entry<Tuple<ItemStack, ItemStack>, Tuple<ItemStack, Integer>> entry : resultsList.entrySet()) {
            recipes.add(new InfuserRecipeWrapper(entry.getKey().getFirst(), entry.getKey().getSecond(), entry.getValue().getSecond(), entry.getValue().getFirst()));
        }

        recipes.sort(Comparator.comparing(o -> o.getOutput().getItem().getUnlocalizedName()));
        registry.addRecipes(recipes, getUid());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, InfuserRecipeWrapper infuserRecipeWrapper, IIngredients iIngredients) {
        iRecipeLayout.getItemStacks().init(0, true, 56, 8);
        iRecipeLayout.getItemStacks().init(1, true, 56, 27);

        iRecipeLayout.getItemStacks().set(0, infuserRecipeWrapper.getInput1());
        iRecipeLayout.getItemStacks().set(1, infuserRecipeWrapper.getInput2());

        iRecipeLayout.getItemStacks().init(2, false, 110, 15);
        iRecipeLayout.getItemStacks().set(2, infuserRecipeWrapper.getOutput());
    }
}
