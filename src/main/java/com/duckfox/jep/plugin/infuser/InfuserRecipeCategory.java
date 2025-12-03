package com.duckfox.jep.plugin.infuser;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import com.pixelmonmod.pixelmon.api.recipe.InfuserRecipes;
import com.pixelmonmod.pixelmon.client.gui.machines.infuser.GuiInfuser;
import com.pixelmonmod.pixelmon.config.PixelmonBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;

public class InfuserRecipeCategory extends DuckRecipeCategory<InfuserRecipeWrapper> {

    public static final RecipeType<InfuserRecipeWrapper> TYPE = RecipeType.create("justenoughpixelmon", "infuser", InfuserRecipeWrapper.class);

    public InfuserRecipeCategory(IGuiHelper helper) {
        super("infuser", TYPE, helper.drawableBuilder(GuiInfuser.infuserGuiTextures, 25, 13, 135, 50).build(), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(PixelmonBlocks.infuser)));
    }

    @Override
    public List<InfuserRecipeWrapper> getRecipes() {
        List<InfuserRecipeWrapper> recipes = new ArrayList<>();
        for (Tuple<ItemStack, ItemStack> key : InfuserRecipes.instance().getResultsList().keySet()) {
            Tuple<ItemStack, Integer> value = InfuserRecipes.instance().getResultsList().get(key);
            recipes.add(new InfuserRecipeWrapper(key.getFirst(), key.getSecond(), value.getSecond(), value.getFirst()));
        }
        return recipes;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InfuserRecipeWrapper recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 8).addItemStack(recipe.getInput1());
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 27).addItemStack(recipe.getInput2());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 110, 15).addItemStack(recipe.getOutput());
    }
}
