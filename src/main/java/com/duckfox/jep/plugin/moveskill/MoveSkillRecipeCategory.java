package com.duckfox.jep.plugin.moveskill;

import com.duckfox.jep.plugin.DuckRecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public abstract class MoveSkillRecipeCategory<T extends MoveSkillRecipeWrapper> extends DuckRecipeCategory<T> {
    public MoveSkillRecipeCategory(String titleId, RecipeType<T> type, IGuiHelper helper) {
        super(titleId, type, helper.createBlankDrawable(170,40), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.PAPER)));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MoveSkillRecipeWrapper recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 81, 12).addItemStack(recipe.moveSkillItem.getItemStack());
    }
}
